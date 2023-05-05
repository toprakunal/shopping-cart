package com.example.TomDemo2.dto.converter;

import com.example.TomDemo2.dto.CartDto;
import com.example.TomDemo2.model.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartDtoConverter {

    public CartDto convert(Cart cart){
        return CartDto.builder()
                .item(cart.getItems())
                .finalValue(cart.getFinalValue())
                .totalPrice(cart.getTotalPrice())
                .coupon(cart.getCoupon())
                .build();
    }
}
