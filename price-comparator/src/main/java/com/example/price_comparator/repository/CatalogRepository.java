package com.example.price_comparator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.price_comparator.domain.Catalog;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
}
