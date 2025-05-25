package com.example.price_comparator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;

import com.example.price_comparator.repository.PriceAlertRepository;
import com.example.price_comparator.dto.PriceAlertDTO;
import com.example.price_comparator.dto.PriceAlertWithProductsDTO;
import com.example.price_comparator.dto.PriceHistoryDTO;
import com.example.price_comparator.dto.PricePointDTO;
import com.example.price_comparator.dto.PricedProductDTO;
import com.example.price_comparator.mapper.PriceAlertMapper;

@Service
public class PriceAlertService {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Autowired
    private PriceAlertMapper priceAlertMapper;

    public void save(PriceAlertDTO priceAlertDTO) {
        priceAlertRepository.save(priceAlertMapper.toEntity(priceAlertDTO));
    }

    public List<PriceAlertDTO> findAllPriceAlerts() {
        return priceAlertMapper.toDtoList(priceAlertRepository.findAll());
    }

    public List<PriceAlertWithProductsDTO> findAllTriggeredPriceAlerts() {
        List<PriceAlertWithProductsDTO> triggeredAlerts = new ArrayList<>();
        List<PriceAlertDTO> alerts = priceAlertMapper.toDtoList(priceAlertRepository.findAll());               

        alerts.forEach(alert -> {
            List<PriceHistoryDTO> priceHistoryList = catalogService.getPriceHistory(alert.getProductName());

            List<Map.Entry<PriceHistoryDTO, PricePointDTO>> allBelowTargetPrice = priceHistoryList.stream()
                .flatMap(history -> history.getPriceHistory().stream()
                    .map(point -> (Map.Entry<PriceHistoryDTO, PricePointDTO>)
                        new AbstractMap.SimpleEntry<>(history, point)))
                .filter(entry -> entry.getValue().getPrice() <= alert.getTargetPrice())
                .collect(Collectors.toList());
            allBelowTargetPrice.sort(Comparator.comparingDouble(entry -> entry.getValue().getPrice()));
            
            List<PricedProductDTO> products = new ArrayList<>();
            allBelowTargetPrice.forEach(entry -> {
                PriceHistoryDTO priceHistory = entry.getKey();
                PricePointDTO pricePoint = entry.getValue();
                PricedProductDTO pricedProduct = new PricedProductDTO(
                    priceHistory.getId(),
                    priceHistory.getName(),
                    priceHistory.getCategory(),
                    priceHistory.getBrand(),
                    priceHistory.getQuantity(),
                    priceHistory.getUnit(),
                    pricePoint.getPrice(),
                    pricePoint.getCurrency(),
                    pricePoint.getShopName(),
                    pricePoint.getDate()
                );
                products.add(pricedProduct);
            });

            triggeredAlerts.add(new PriceAlertWithProductsDTO(
                alert.getProductName(),
                alert.getTargetPrice(),
                alert.getUserEmail(),
                products
            ));
        });

        return triggeredAlerts;
    }
}
