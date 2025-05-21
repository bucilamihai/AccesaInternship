package com.example.price_comparator.repository;

import com.example.price_comparator.domain.PricedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricedProductRepository extends JpaRepository<PricedProduct, Long> {
}
