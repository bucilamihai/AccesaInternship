package com.example.price_comparator.domain;

    import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Discount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private PricedProduct pricedProduct;

    private LocalDate startDate;
    private LocalDate endDate;
    private int discountPercentage;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;
}