package com.example.price_comparator.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PricedProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    private double price;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    public PricedProduct(Product product, double price, String currency) {
        this.product = product;
        this.price = price;
        this.currency = currency;
    }
}

