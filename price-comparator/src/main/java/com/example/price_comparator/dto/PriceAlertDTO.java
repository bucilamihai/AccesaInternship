package com.example.price_comparator.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PriceAlertDTO {
    private String productName;
    private double targetPrice;
    private String userEmail;
}
