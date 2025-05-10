package com.example.price_comparator.repository.in_memory;

import org.springframework.stereotype.Repository;

import com.example.price_comparator.domain.Product;
import com.example.price_comparator.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> productMap = new HashMap<>();

    @Override
    public void save(Product product) {
        productMap.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

}
