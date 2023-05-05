package com.example.TomDemo2.dto;


import com.example.TomDemo2.model.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {



    private Product product;

    private int quantity;



}
