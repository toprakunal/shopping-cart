package com.example.TomDemo2.dto.converter;

import com.example.TomDemo2.dto.ItemDto;
import com.example.TomDemo2.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoConverter {

    public ItemDto convert(Item item){
        return ItemDto.builder()
                .product(item.getProduct())
                .quantity(item.getQuantity())
                .build();
    }
}
