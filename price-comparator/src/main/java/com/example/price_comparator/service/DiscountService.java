package com.example.price_comparator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.price_comparator.repository.in_memory.InMemoryDiscountRepository;
import com.example.price_comparator.repository.in_memory.InMemoryProductRepository;
import com.example.price_comparator.domain.Discount;

@Service
public class DiscountService {
    
    @Autowired
    private InMemoryDiscountRepository discountRepository;
    @Autowired
    private InMemoryProductRepository productRepository;

    public List<Discount> findAll() {
        System.out.println(discountRepository.findAll().size());
        return discountRepository.findAll();
    }
}
