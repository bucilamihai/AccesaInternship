package com.example.price_comparator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PriceHistoryDTO {
    private String id;
    private String name;
    private String brand;
    private String category;
    private String unit;
    private double quantity;

    private List<PricePointDTO> priceHistory;

    public PriceHistoryDTO(String productId, String name, String brand, String category, String unit, double quantity) {
        this.id = productId;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.unit = unit;
        this.quantity = quantity;
    }

    public void addPricePoint(PricePointDTO pricePointDTO) {
        this.priceHistory.add(pricePointDTO);
    }
}
