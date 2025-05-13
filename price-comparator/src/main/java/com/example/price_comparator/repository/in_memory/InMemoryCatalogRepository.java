package com.example.price_comparator.repository.in_memory;

import com.example.price_comparator.domain.Catalog;
import com.example.price_comparator.repository.CatalogRepository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCatalogRepository implements CatalogRepository {
    private List<Catalog> catalogs = new ArrayList<>();

    @Override
    public void save(Catalog catalog) {
        catalogs.add(catalog);
    }

    @Override
    public List<Catalog> findAll() {
        return catalogs;
    }
}
