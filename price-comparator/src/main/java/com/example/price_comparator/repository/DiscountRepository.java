package com.example.price_comparator.repository;

import com.example.price_comparator.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>{
    
    List<Discount> findByStartDate(LocalDate date);

    @Query("SELECT d FROM Discount d ORDER BY d.discountPercentage DESC LIMIT ?1")
    List<Discount> findTopByOrderByDiscountPercentageDesc(int limit);

    @Query("SELECT d FROM Discount d ORDER BY d.discountPercentage DESC")
    List<Discount> findAllByOrderByDiscountPercentageDesc();
}
