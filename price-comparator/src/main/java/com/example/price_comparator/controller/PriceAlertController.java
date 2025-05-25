package com.example.price_comparator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.price_comparator.dto.PriceAlertWithProductsDTO;
import com.example.price_comparator.service.PriceAlertService;

@RestController
@RequestMapping("/alerts")
@CrossOrigin(value = "http://localhost:8080")
public class PriceAlertController {
    
    @Autowired
    private PriceAlertService priceAlertService;

    @GetMapping("")
    public ResponseEntity<Object> getPriceAlerts() {
        return ResponseEntity.status(HttpStatus.OK).body(priceAlertService.findAllPriceAlerts());
    }

    @GetMapping("/triggered")
    public ResponseEntity<List<PriceAlertWithProductsDTO>> getTriggeredPriceAlerts() {
        return ResponseEntity.status(HttpStatus.OK).body(priceAlertService.findAllTriggeredPriceAlerts());
    }
}
