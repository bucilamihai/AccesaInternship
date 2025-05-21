package com.example.price_comparator.service;

import com.example.price_comparator.repository.CatalogRepository;
import com.example.price_comparator.domain.Catalog;

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

    @Transactional
    public void saveCatalog(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    public List<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    public List<Discount> filterDiscountsByDate(LocalDate date) {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<Discount> discounts = catalogs.stream()
                .flatMap(catalog -> catalog.getDiscounts().stream())
                .filter(discount -> discount.getStartDate().equals(date))
                .toList();
        return discounts;
    }

    public List<Discount> findBestDiscounts() {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<Discount> discounts = catalogs.stream()
                .flatMap(catalog -> catalog.getDiscounts().stream())
                .sorted((d1, d2) -> Integer.compare(d2.getDiscountPercentage(), d1.getDiscountPercentage()))
                .toList();
        return discounts;
    }
}
