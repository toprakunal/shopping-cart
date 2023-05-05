package com.example.TomDemo2.repository;

import com.example.TomDemo2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
