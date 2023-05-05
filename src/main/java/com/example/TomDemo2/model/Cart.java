package com.example.TomDemo2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    private int id;

    private double totalPrice;

    @OneToOne(mappedBy = "cart")
    @JsonIgnore
    private Coupon coupon;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private List<Item> items;

    private double finalValue;


}
