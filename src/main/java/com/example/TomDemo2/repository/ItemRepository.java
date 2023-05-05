package com.example.TomDemo2.repository;

import com.example.TomDemo2.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
