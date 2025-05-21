package com.example.price_comparator.service;

import com.example.price_comparator.repository.CatalogRepository;
import com.example.price_comparator.domain.Catalog;
import com.example.price_comparator.mapper.DiscountMapper;
import com.example.price_comparator.dto.DiscountDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

import com.example.price_comparator.domain.Discount;

@Service
public class CatalogService {
    
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private DiscountMapper discountMapper;

    @Transactional
    public void saveCatalog(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    public List<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    public List<DiscountDTO> filterDiscountsByDate(LocalDate date) {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<Discount> discounts = catalogs.stream()
                .flatMap(catalog -> catalog.getDiscounts().stream())
                .filter(discount -> discount.getStartDate().equals(date))
                .toList();
        return discountMapper.toDtoList(discounts);
    }

    public List<DiscountDTO> findBestDiscounts(Integer limit) {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<Discount> discounts = catalogs.stream()
                .flatMap(catalog -> catalog.getDiscounts().stream())
                .sorted((d1, d2) -> Integer.compare(d2.getDiscountPercentage(), d1.getDiscountPercentage()))
                .toList();
        if (limit != null && limit > 0) {
            discounts = discounts.stream().limit(limit).toList();
        }
        return discountMapper.toDtoList(discounts);
    }
}
