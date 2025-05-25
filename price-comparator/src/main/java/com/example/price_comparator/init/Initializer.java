package com.example.price_comparator.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.price_comparator.service.PriceAlertService;
import com.example.price_comparator.dto.PriceAlertDTO;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {
   
    @Autowired
    private CatalogCsvLoader csvCatalogLoader;

    @Autowired
    private PriceAlertService priceAlertService;

    private final String shopsFolderPath = "src/main/resources/shops";
    private final String priceAlertsFilePath = "src/main/resources/price_alerts.txt";

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        List<String> shopNames = loadShops(shopsFolderPath);
        shopNames.forEach(shopName -> {
            csvCatalogLoader.loadCatalogs(shopsFolderPath + "/" + shopName, shopName);
        });

        loadPriceAlerts(priceAlertsFilePath);
    }

    public List<String> loadShops(String folderPath) {
        List<String> shopNames = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles(File::isDirectory);
        if (files != null) {
            for (File file : files) {
                String shopName = file.getName();
                shopNames.add(shopName);
            }
        } else {
            System.err.println("No folders found in the path: " + folderPath);
        }

        return shopNames;
    }

    public void loadPriceAlerts(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Price alerts file not found: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    System.err.println("Invalid price alert format: " + line);
                    continue;
                }
                String productName = parts[0].trim();
                double targetPrice;
                try {
                    targetPrice = Double.parseDouble(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid price format for product: " + productName);
                    continue;
                }
                String email = parts[2].trim();

                PriceAlertDTO priceAlertDTO = new PriceAlertDTO(productName, targetPrice, email);
                priceAlertService.save(priceAlertDTO);
            }
            System.out.println("Price alerts loaded successfully from: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading price alerts file: " + e.getMessage());
        }
    }
}
