package com.example.price_comparator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.price_comparator.domain.Catalog;
import com.example.price_comparator.service.CatalogService;

@RestController
@RequestMapping("/catalogs")
@CrossOrigin(value = "http://localhost:8080")
public class CatalogController {
    
    @Autowired
    private CatalogService catalogService;

    @GetMapping("")
    public ResponseEntity<List<Catalog>> getAllCatalogs() {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.findAll());
    }
    
}
