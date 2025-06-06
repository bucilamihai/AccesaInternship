package com.example.price_comparator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String category;
    private String brand;
    private double quantity;
    private String unit;
}
