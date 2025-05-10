package com.example.price_comparator.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.price_comparator.repository.in_memory.InMemoryProductRepository;
import com.example.price_comparator.domain.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

@Component
public class ProductCsvLoader {

    @Autowired
    private InMemoryProductRepository productRepository;
    private final String folderPath = "src/main/resources/products";

    public void loadProducts() {
        System.out.println("Loading products from folder: " + folderPath);
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
                if (data.length == 8) {
                    String id = data[0];
                    String name = data[1];
                    String category = data[2];
                    String brand = data[3];
                    double quantity = Double.parseDouble(data[4]);
                    String unit = data[5];
                    double price = Double.parseDouble(data[6]);
                    String currency = data[7];

                    Product product = new Product(id, name, category, brand, quantity, unit, price, currency);
                    productRepository.save(product);
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
