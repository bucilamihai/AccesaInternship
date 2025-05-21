package com.example.price_comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PricePointDTO {
    private String shopName;
    private LocalDate date;
    private double price;
    private String currency;
    private int discountPercentage;
}
