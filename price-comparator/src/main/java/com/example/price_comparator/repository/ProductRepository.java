package com.example.price_comparator.repository;

import com.example.price_comparator.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();
}
