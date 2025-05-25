package com.example.price_comparator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BasketDTO {
    private List<BasketItemDTO> items;
}