package com.example.price_comparator.repository.in_memory;

import org.springframework.stereotype.Repository;

import com.example.price_comparator.domain.Discount;
import com.example.price_comparator.repository.DiscountRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class InMemoryDiscountRepository implements DiscountRepository {
    private final Map<String, List<Discount>> discountMap = new HashMap<>();

    @Override
    public void save(Discount discount) {
        discountMap.computeIfAbsent(discount.getProduct().getId(), k -> new ArrayList<>()).add(discount);
    }

    @Override
    public List<Discount> findByProductId(String productId) {
        return discountMap.getOrDefault(productId, new ArrayList<>());
    }

    @Override
    public List<Discount> findAll() {
        return discountMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }
    
}
