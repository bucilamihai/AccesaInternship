package com.example.price_comparator.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.price_comparator.domain.PriceAlert;
import com.example.price_comparator.dto.PriceAlertDTO;

@Mapper(componentModel = "spring")
public interface PriceAlertMapper {
    @Mappings({
        @Mapping(source = "productName", target = "productName"),
        @Mapping(source = "targetPrice", target = "targetPrice"),
        @Mapping(source = "userEmail", target = "userEmail")
    })

    PriceAlertDTO toDto(PriceAlert priceAlert);
    List<PriceAlertDTO> toDtoList(List<PriceAlert> priceAlerts);

    PriceAlert toEntity(PriceAlertDTO priceAlertDTO);
}
