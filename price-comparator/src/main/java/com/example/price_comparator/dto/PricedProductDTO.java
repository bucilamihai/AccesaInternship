package com.example.price_comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PricedProductDTO {
    private String productId;
    private String name;
    private String category;
    private String brand;
    private double quantity;
    private String unit;

    private double price;
    private String currency;

    private String shopName;
    private String catalogDate;
}