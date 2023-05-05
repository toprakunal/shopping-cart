package com.example.TomDemo2.controller;


import com.example.TomDemo2.dto.CartDto;
import com.example.TomDemo2.dto.ItemDto;
import com.example.TomDemo2.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}/items")
    public List<ItemDto> getItems(@PathVariable int cartId){
        return cartService.findAllItems(cartId);
    }

    @GetMapping
    public List<CartDto> getCarts(){
        return cartService.findCarts();
    }

    @PostMapping("/{cartId}/items/add")
    public void addItemToCart(@PathVariable int cartId, @RequestParam int productId, @RequestParam int quantity){
        cartService.addItemToCart(cartId,productId,quantity);
    }

    @PostMapping("/{cartId}/items/remove")
    public void removeItemFromCart(@PathVariable int cartId, @RequestParam int productId, @RequestParam int quantity){
        cartService.removeItemFromCart(cartId,productId,quantity);
    }
    @PostMapping("/{cartId}/coupon/add")
    public void applyCouponToCart(@PathVariable int cartId, @RequestParam int couponId){
        cartService.applyCouponToCart(cartId,couponId);
    }

    @PostMapping("/{cartId}/coupon/remove")
    public void removeCouponFromCart(@PathVariable int cartId){
        cartService.removeCouponFromCart(cartId);
    }


}
