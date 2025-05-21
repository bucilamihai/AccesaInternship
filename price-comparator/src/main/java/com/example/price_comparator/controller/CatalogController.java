package com.example.price_comparator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.price_comparator.dto.PriceHistoryDTO;
import com.example.price_comparator.service.CatalogService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/catalogs")
@CrossOrigin(value = "http://localhost:8080")
public class CatalogController {
    
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/price-history")
    public ResponseEntity<List<PriceHistoryDTO>> getPriceHistory(@RequestParam String productName) {
        List<PriceHistoryDTO> priceHistory = catalogService.getPriceHistory(productName);
        return ResponseEntity.status(HttpStatus.OK).body(priceHistory);
    }
}
