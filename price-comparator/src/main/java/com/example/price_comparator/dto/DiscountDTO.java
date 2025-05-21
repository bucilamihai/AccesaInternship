package com.example.price_comparator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DiscountDTO {
    private PricedProductDTO pricedProduct;
    private String startDate;
    private String endDate;
    private int discountPercentage;
}