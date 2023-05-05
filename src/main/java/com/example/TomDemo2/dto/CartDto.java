package com.example.TomDemo2.dto;

import com.example.TomDemo2.model.Coupon;
import com.example.TomDemo2.model.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartDto {

    private double totalPrice;

    private double finalValue;

    private List<Item> item;

    private Coupon coupon;

}
