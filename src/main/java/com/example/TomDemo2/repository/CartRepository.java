package com.example.TomDemo2.repository;

import com.example.TomDemo2.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
