package com.example.price_comparator.repository;

import com.example.price_comparator.domain.Catalog;

import java.util.List;

public interface CatalogRepository {
    void save(Catalog catalog);
    List<Catalog> findAll();
}
