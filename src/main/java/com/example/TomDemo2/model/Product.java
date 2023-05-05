package com.example.TomDemo2.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    private int id;

    private double price;

    private String name;

    @OneToOne(mappedBy = "product")
    @JsonIgnore
    private Item item;

    private String productDescription;

    private String image;

}
