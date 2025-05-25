package com.example.price_comparator.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.price_comparator.domain.PriceAlert;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByProductName(String productName); 
    List<PriceAlert> findByUserEmail(String userEmail);
}
