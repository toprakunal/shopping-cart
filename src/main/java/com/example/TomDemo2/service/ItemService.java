package com.example.TomDemo2.service;

import com.example.TomDemo2.model.Item;
import com.example.TomDemo2.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public void deleteItem(int id){
        itemRepository.deleteById(id);
    }


}
