package com.example.price_comparator.mapper;

import com.example.price_comparator.domain.PricedProduct;
import com.example.price_comparator.dto.PricedProductDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PricedProductMapper {

    @Mappings({
        @Mapping(source = "product.id", target = "productId"),
        @Mapping(source = "product.name", target = "name"),
        @Mapping(source = "product.category", target = "category"),
        @Mapping(source = "product.brand", target = "brand"),
        @Mapping(source = "product.quantity", target = "quantity"),
        @Mapping(source = "product.unit", target = "unit"),

        @Mapping(source = "price", target = "price"),
        @Mapping(source = "currency", target = "currency"),

        @Mapping(source = "catalog.shopName", target = "shopName"),
        @Mapping(source = "catalog.catalogDate", target = "catalogDate")
    })
    PricedProductDTO toDto(PricedProduct pricedProduct);

    List<PricedProductDTO> toDtoList(List<PricedProduct> discounts);
}