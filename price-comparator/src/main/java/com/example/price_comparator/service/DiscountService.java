package com.example.price_comparator.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.price_comparator.domain.Discount;
import com.example.price_comparator.dto.DiscountDTO;
import com.example.price_comparator.mapper.DiscountMapper;
import com.example.price_comparator.repository.DiscountRepository;

@Service
public class DiscountService {
    
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    public List<DiscountDTO> filterDiscountsByDate(LocalDate date) {
        List<Discount> discounts = discountRepository.findByStartDate(date);
        return discountMapper.toDtoList(discounts);
    }

    public List<DiscountDTO> findBestDiscounts(Integer limit) {
        List<Discount> discounts = (limit != null && limit > 0)
            ? discountRepository.findTopByOrderByDiscountPercentageDesc(limit)
            : discountRepository.findAllByOrderByDiscountPercentageDesc();
        return discountMapper.toDtoList(discounts);
    }
}
