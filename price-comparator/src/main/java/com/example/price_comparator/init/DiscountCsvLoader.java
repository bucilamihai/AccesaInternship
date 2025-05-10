package com.example.price_comparator.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.price_comparator.repository.in_memory.InMemoryDiscountRepository;
import com.example.price_comparator.repository.in_memory.InMemoryProductRepository;
import com.example.price_comparator.domain.Product;
import com.example.price_comparator.domain.Discount;

import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

@Component
public class DiscountCsvLoader {

    @Autowired
    private InMemoryDiscountRepository discountRepository;
    @Autowired
    private InMemoryProductRepository productRepository;
    private final String folderPath = "src/main/resources/discounts";

    public void loadDiscounts() {
        System.out.println("Loading discounts from folder: " + folderPath);
        // Search for all CSV files in the folder
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
                loadDataFromCsv(file.getAbsolutePath());
            }
        } else {
            System.err.println("No CSV files found in the folder: " + folderPath);
        }
    }

    public void loadDataFromCsv(String filePath)  {
        System.out.println("Loading data from CSV file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Header line
            String header = reader.readLine();
            if (header == null || header.isEmpty()) {
                System.err.println("Empty or invalid CSV file: " + filePath);
                return;
            }

            reader.lines().forEach(line ->{
                String[] data = line.split(",");
                if (data.length == 9) {
                    String id = data[0];
                    LocalDate startDate = LocalDate.parse(data[6]);
                    LocalDate endDate = LocalDate.parse(data[7]);
                    int discountPercentage = Integer.parseInt(data[8]);
                    Product product = productRepository.findById(id).orElse(null);
                    Discount discount = new Discount(product, startDate, endDate, discountPercentage);
                    discountRepository.save(discount);
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            });
            System.out.println("Data loaded successfully from CSV. " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading data from CSV: " + e.getMessage());
        }
    }
}
