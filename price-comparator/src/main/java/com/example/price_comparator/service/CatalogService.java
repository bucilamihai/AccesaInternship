package com.example.price_comparator.service;

import com.example.price_comparator.repository.CatalogRepository;
import com.example.price_comparator.domain.Catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    
    @Autowired
    private CatalogRepository catalogRepository;

    public void saveCatalog(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    public List<Catalog> findAll() {
        return catalogRepository.findAll();
    }
}
