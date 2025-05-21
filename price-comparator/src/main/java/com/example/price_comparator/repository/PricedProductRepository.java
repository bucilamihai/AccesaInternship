package com.example.price_comparator.repository;

import com.example.price_comparator.domain.PricedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricedProductRepository extends JpaRepository<PricedProduct, Long> {
   
    List<PricedProduct> findByProductId(String productId);
}
