package com.example.price_comparator.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Component
public class Initializer {
    
    @Autowired
    private ProductCsvLoader productCsvLoader;
    @Autowired
    private DiscountCsvLoader discountCsvLoader;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        productCsvLoader.loadProducts();
        discountCsvLoader.loadDiscounts();
    }
}
