package com.example.price_comparator.mapper;

import com.example.price_comparator.domain.Discount;
import com.example.price_comparator.dto.DiscountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PricedProductMapper.class})
public interface DiscountMapper {

    @Mapping(source = "pricedProduct", target = "pricedProduct")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "discountPercentage", target = "discountPercentage")
    DiscountDTO toDto(Discount discount);

    List<DiscountDTO> toDtoList(List<Discount> discounts);
}
