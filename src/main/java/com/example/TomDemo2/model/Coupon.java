package com.example.TomDemo2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Coupon {

    @Id
    private int id;


    @OneToOne
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;

    private DiscountType discountType;

    private double value;

    private String couponDescription;


}
