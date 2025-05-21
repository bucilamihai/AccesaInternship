package com.example.price_comparator.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Product {

    @Id
    private String id;

    private String name;
    private String category;
    private String brand;
    private double quantity;
    private String unit;
}
