package com.example.price_comparator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import com.example.price_comparator.dto.DiscountDTO;
import com.example.price_comparator.service.DiscountService;

@RestController
@RequestMapping("/discounts")
@CrossOrigin(value = "http://localhost:8080")
public class DiscountsController {
    
    @Autowired
    private DiscountService discountService;

    @GetMapping("/new") 
    public ResponseEntity<List<DiscountDTO>> getNewDiscounts(@RequestParam LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(discountService.filterDiscountsByDate(date));
    }

    @GetMapping("/best") 
    public ResponseEntity<List<DiscountDTO>> getBestDiscounts(@RequestParam(required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(discountService.findBestDiscounts(limit));
    }
}
