package com.example.TomDemo2.service;


import com.example.TomDemo2.model.Product;
import com.example.TomDemo2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }
}
