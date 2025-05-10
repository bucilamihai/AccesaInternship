package com.example.price_comparator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.price_comparator.repository.in_memory.InMemoryProductRepository;
import com.example.price_comparator.domain.Product;

@Service
public class ProductService {
    
    @Autowired
    private InMemoryProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
