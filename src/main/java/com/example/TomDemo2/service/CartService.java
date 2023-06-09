package com.example.TomDemo2.service;


import com.example.TomDemo2.dto.CartDto;
import com.example.TomDemo2.dto.ItemDto;
import com.example.TomDemo2.dto.converter.CartDtoConverter;
import com.example.TomDemo2.dto.converter.ItemDtoConverter;
import com.example.TomDemo2.exception.CartNotFoundException;
import com.example.TomDemo2.exception.CouponNotFoundException;
import com.example.TomDemo2.exception.ProductNotFoundException;
import com.example.TomDemo2.model.*;
import com.example.TomDemo2.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService  {

    private final CartRepository cartRepository;
    private final ProductService productService;

    private final ItemDtoConverter itemDtoConverter;

    private final CartDtoConverter cartDtoConverter;

    private final ItemService itemService;

    private final CouponService couponService;

    public CartService(CartRepository cartRepository, ProductService productService, ItemDtoConverter itemDtoConverter, CartDtoConverter cartDtoConverter, ItemService itemService, CouponService couponService) {
        this.cartRepository = cartRepository;

        this.productService = productService;
        this.itemDtoConverter = itemDtoConverter;
        this.cartDtoConverter = cartDtoConverter;
        this.itemService = itemService;
        this.couponService = couponService;
    }

    public void addItemToCart(int cartId, int productId, int quantity){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
        Product product = productService.findProductById(productId).orElseThrow(() ->new ProductNotFoundException("Product Not Found"));

            Item item;

            if(product.getItem() == null){
                item =Item.builder().
                        id(productId).
                        product(product)
                        .cart(cart)
                        .quantity(quantity)
                        .build();

            }else{
                 item = product.getItem();
                item.setQuantity(item.getQuantity() + quantity);  // 2+1 = 3

            }

            item.setSubTotal(item.getQuantity() * product.getPrice());
            double currentTotal = quantity* product.getPrice();
            cart.setTotalPrice(cart.getTotalPrice() + currentTotal); // 10
            cart.setFinalValue(cart.getFinalValue() + currentTotal);
            cartRepository.save(cart);
            itemService.saveItem(item);



        }



    public void removeItemFromCart(int cartId, int productId, int quantity){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
        Product product = productService.findProductById(productId).orElseThrow(() ->new ProductNotFoundException("Product Not Found"));

            Item item = product.getItem();

            if(item != null){
                 if ((item.getQuantity() - quantity) >= 0){
                    item.setQuantity(item.getQuantity() - quantity);
                    item.setSubTotal(item.getQuantity() * product.getPrice());
                    double currentTotal = quantity * product.getPrice();
                    cart.setTotalPrice(cart.getTotalPrice() - currentTotal); // 10
                    cart.setFinalValue(cart.getFinalValue() - currentTotal);
                    cartRepository.save(cart);
                    itemService.saveItem(item);
                    if(item.getQuantity() <= 0){
                        itemService.deleteItem(item.getId());
                    }
                }
            }




    }

    public void applyCouponToCart(int cartId, int couponId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
        Coupon coupon = couponService.findCouponById(couponId).orElseThrow(() -> new CouponNotFoundException("Coupon Not Found"));


            if(cart.getCoupon() == null){

                if(cart.getFinalValue() > 0 ){
                    coupon.setCart(cart);
                    if(coupon.getDiscountType().equals(DiscountType.AMOUNT)){
                        cart.setFinalValue(cart.getFinalValue() - coupon.getValue() );
                    }if(coupon.getDiscountType().equals(DiscountType.RATIO)){
                        cart.setFinalValue(cart.getFinalValue() - (cart.getFinalValue()* (coupon.getValue()/100)));
                    }
                    cartRepository.save(cart);
                    couponService.save(coupon);
                }
            }

        }



    public void removeCouponFromCart(int cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException("Cart Not Found"));




            Coupon coupon = cart.getCoupon();
            if(coupon != null){
                cart.setFinalValue(cart.getTotalPrice());
                cart.setCoupon(null);
                coupon.setCart(null);
                cartRepository.save(cart);
                couponService.save(coupon);
            }

    }





    public List<ItemDto> findAllItems(int cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException("Cart Not Found"));

            return cart.getItems().stream().map(itemDtoConverter::convert).collect(Collectors.toList());
        }


    public List<CartDto> findCarts(){

        return cartRepository.findAll().stream().map(cartDtoConverter::convert).collect(Collectors.toList());
    }






}
