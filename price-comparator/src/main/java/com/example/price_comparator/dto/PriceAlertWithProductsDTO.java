package com.example.price_comparator.dto;

import java.util.List;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PriceAlertWithProductsDTO {
    private String productName;
    private double targetPrice;
    private String userEmail;

    private List<PricedProductDTO> pricedProducts;
}
