package com.example.price_comparator.service;

import com.example.price_comparator.repository.CatalogRepository;
import com.example.price_comparator.repository.DiscountRepository;
import com.example.price_comparator.repository.ProductRepository;
import com.example.price_comparator.utils.MathUtils;
import com.example.price_comparator.repository.PricedProductRepository;
import com.example.price_comparator.domain.Catalog;
import com.example.price_comparator.domain.PricedProduct;
import com.example.price_comparator.domain.Discount;
import com.example.price_comparator.dto.PriceHistoryDTO;
import com.example.price_comparator.dto.PricePointDTO;
import com.example.price_comparator.dto.ShoppingListDTO;
import com.example.price_comparator.dto.BasketDTO;
import com.example.price_comparator.dto.PricedProductDTO;

import java.util.Map;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.DoubleAdder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.price_comparator.domain.Product;

@Service
public class CatalogService {
    
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PricedProductRepository pricedProductRepository;
    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public void saveCatalog(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    public List<PriceHistoryDTO> getPriceHistory(String productName) {
        List<Product> products = productRepository.findByName(productName);
        if (products.isEmpty()) {
            return null; // should throw an exception
        }
        
        List<PricedProduct> pricedProducts = new ArrayList<>();
        products.forEach(product -> {
            pricedProducts.addAll(pricedProductRepository.findByProductId(product.getId()));
        });
        
        List<Discount> discounts = new ArrayList<>();
        pricedProducts.forEach(pricedProduct -> {
            discounts.addAll(discountRepository.findByPricedProductId(pricedProduct.getId()));
        });

        // create product
        List<PriceHistoryDTO> priceHistory = new ArrayList<>();
        products.forEach(product -> {
            PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getUnit(),
                product.getQuantity()
            );
            priceHistoryDTO.setPriceHistory(new ArrayList<>());
            priceHistory.add(priceHistoryDTO);
        });

        // find price history
        priceHistory.forEach(product -> {
            // add normal price point
            List<PricedProduct> localPricedProducts = pricedProducts.stream()
                .filter(pricedProduct -> product.getId() == pricedProduct.getProduct().getId())
                .toList();
            localPricedProducts.forEach(pricedProduct -> {
                String shopName = pricedProduct.getCatalog().getShopName();
                double price = pricedProduct.getPrice();
                String currency = pricedProduct.getCurrency();
                int discountPercentage = 0; // we assume product doesn't have any discount
                LocalDate date = pricedProduct.getCatalog().getCatalogDate();   
             
                for(int i = 0; i < 7; i++) {
                    product.addPricePoint(new PricePointDTO(
                        shopName,
                        date,
                        price,
                        currency,
                        discountPercentage
                    ));
                    date = date.plusDays(1);
                }
            });
        });

        priceHistory.forEach(product -> {
            // discounted price point
            List<Discount> localDiscounts = discounts.stream()
                .filter(discount -> product.getId() == discount.getPricedProduct().getProduct().getId())
                .toList();
            localDiscounts.forEach(discount -> {
                for(LocalDate date = discount.getStartDate(); 
                    date.isBefore(discount.getEndDate());
                    date = date.plusDays(1)) {
                    // update price point
                    LocalDate currentDate = date;
                    product.getPriceHistory().forEach(pricePoint -> {
                        if (pricePoint.getDate().isEqual(currentDate)) {
                            pricePoint.setPrice(
                                MathUtils.round(
                                pricePoint.getPrice() - 
                                MathUtils.calculatePercentage(
                                    pricePoint.getPrice(),
                                    discount.getDiscountPercentage()
                                ),
                                2
                            ));
                            pricePoint.setDiscountPercentage(discount.getDiscountPercentage());
                        }
                    });
                }
            });
        });
        return priceHistory;
    }

    public ShoppingListDTO optimiseBasket(BasketDTO basketDTO, LocalDate givenDate) {
        DoubleAdder totalPrice = new DoubleAdder();
        List<PricedProductDTO> items = new ArrayList<>();

        basketDTO.getItems().forEach(basketItem -> {
            List<PriceHistoryDTO> priceHistoryList = getPriceHistory(basketItem.getProductName());

            Optional<Map.Entry<PriceHistoryDTO, PricePointDTO>> minEntry = priceHistoryList.stream()
                .flatMap(history -> history.getPriceHistory().stream()
                    .filter(point -> point.getDate().isEqual(givenDate))
                    .map(point -> (Map.Entry<PriceHistoryDTO, PricePointDTO>)
                        new AbstractMap.SimpleEntry<>(history, point)))
                .min(Comparator.comparingDouble(entry -> entry.getValue().getPrice()));

            if (minEntry.isPresent()) {
                PriceHistoryDTO priceHistory = minEntry.get().getKey();
                PricePointDTO pricePoint = minEntry.get().getValue();

                totalPrice.add(pricePoint.getPrice() * basketItem.getQuantity());

                PricedProductDTO cheapestProduct = new PricedProductDTO(
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

                items.add(cheapestProduct);
            }
        });

        return new ShoppingListDTO(MathUtils.round(totalPrice.doubleValue(), 2), items);
    }
}
