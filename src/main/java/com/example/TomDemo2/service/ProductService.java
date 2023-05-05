package com.example.TomDemo2.service;


import com.example.TomDemo2.model.Product;
import com.example.TomDemo2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }
}
