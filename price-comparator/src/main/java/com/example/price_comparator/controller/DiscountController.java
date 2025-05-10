package com.example.price_comparator.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.price_comparator.domain.Discount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/discounts")
@CrossOrigin(value = "http://localhost:8080")
public class DiscountController {
    
    // @Autowired
    // private DiscountService discountService;

    //  @GetMapping("")
    // public ResponseEntity<List<Discount>> getAllDiscounts() {
    //     return ResponseEntity.status(HttpStatus.OK).body(discountService.findAll());
    // }
    
}
