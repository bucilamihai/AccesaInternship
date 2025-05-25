package com.example.price_comparator.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.price_comparator.dto.PriceHistoryDTO;
import com.example.price_comparator.dto.BasketDTO;
import com.example.price_comparator.service.CatalogService;
import com.example.price_comparator.dto.ShoppingListDTO;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/optimise-basket")
    public ResponseEntity<?> optimiseBasket(
        @RequestBody BasketDTO basketDTO,
        @RequestParam(name = "date", required = false) LocalDate givenDate
        ) {
        if (basketDTO == null || basketDTO.getItems() == null || basketDTO.getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Basket cannot be empty.");
        }
        if (givenDate == null) {
            givenDate = LocalDate.now();
        }

        ShoppingListDTO shoppingList = catalogService.optimiseBasket(basketDTO, givenDate);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingList);
    }
}
