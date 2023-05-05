package com.example.TomDemo2.service;


import com.example.TomDemo2.dto.CartDto;
import com.example.TomDemo2.dto.ItemDto;
import com.example.TomDemo2.dto.converter.CartDtoConverter;
import com.example.TomDemo2.dto.converter.ItemDtoConverter;
import com.example.TomDemo2.model.*;
import com.example.TomDemo2.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Optional<Product> productOptional = productService.findProductById(productId);

        if(cartOptional.isPresent() && productOptional.isPresent() && quantity>0){
            Cart cart = cartOptional.get();
            Product product = productOptional.get();

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
    }


    public void removeItemFromCart(int cartId, int productId, int quantity){
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Optional<Product> productOptional = productService.findProductById(productId);

        if(cartOptional.isPresent() && productOptional.isPresent()){
            Cart cart = cartOptional.get();
            Product product = productOptional.get();

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




    }}

    public void applyCouponToCart(int cartId, int couponId){
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Optional<Coupon> couponOptional = couponService.findCouponById(couponId);

        if(cartOptional.isPresent() && couponOptional.isPresent() ){
            Cart cart = cartOptional.get();
            Coupon coupon = couponOptional.get();
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
                }else
                    System.out.println("finalvalue is 0");
            }else
                System.out.println("Coupon already exist");

        }
    }

    public void removeCouponFromCart(int cartId){
        Optional<Cart> cartOptional = cartRepository.findById(cartId);


        if(cartOptional.isPresent()  ){
            Cart cart = cartOptional.get();
            Coupon coupon = cart.getCoupon();
            if(coupon != null){
                cart.setFinalValue(cart.getTotalPrice());
                cart.setCoupon(null);
                coupon.setCart(null);
                cartRepository.save(cart);
                couponService.save(coupon);
            }else
                System.out.println("no coupon in cart");
        }else
            System.out.println("no coupon or cart found");

    }





    public List<ItemDto> findAllItems(int cartId){
        Optional<Cart> cartOptional = cartRepository.findById(cartId);

        if(cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            return cart.getItems().stream().map(itemDtoConverter::convert).collect(Collectors.toList());
        }else
            return null;
    }

    public List<CartDto> findCarts(){

        return cartRepository.findAll().stream().map(cartDtoConverter::convert).collect(Collectors.toList());
    }






}
