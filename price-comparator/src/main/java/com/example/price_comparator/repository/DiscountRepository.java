package com.example.price_comparator.repository;

import com.example.price_comparator.domain.Discount;
import java.util.List;

public interface DiscountRepository {
    void save(Discount discount);
    List<Discount> findByProductId(String productId);
    List<Discount> findAll();
}
