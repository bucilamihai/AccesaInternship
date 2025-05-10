package com.example.price_comparator.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.price_comparator.service.CatalogService;
import com.example.price_comparator.domain.Catalog;
import com.example.price_comparator.domain.Product;
import com.example.price_comparator.domain.Discount;

@Component
public class CatalogCsvLoader {

    @Autowired
    private CatalogService catalogService;

    public void loadCatalogs(String folderPath, String shopName) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.err.println("No CSV files found in the path: " + folderPath);
            return;
        }

        // Group by date
        Map<String, File> productFiles = new HashMap<>();
        Map<String, File> discountFiles = new HashMap<>();

        for (File file : files) {
            String fileName = file.getName();
            String date = extractDate(fileName);
            if (fileName.contains("discounts")) {
                discountFiles.put(date, file);
            } else {
                productFiles.put(date, file);
            }
        }

        for (String dateString : productFiles.keySet()) {
            LocalDate date = LocalDate.parse(dateString);
            File productFile = productFiles.get(dateString);
            File discountFile = discountFiles.get(dateString);

            List<Product> products = getProducts(productFile.getAbsolutePath());
            List<Discount> discounts = discountFile != null
                    ? getDiscounts(discountFile.getAbsolutePath(), products)
                    : new ArrayList<>();

            Catalog catalog = new Catalog(shopName, date, products, discounts);
            catalogService.saveCatalog(catalog);
        }
    }

    public List<Product> getProducts(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Header line
            String header = reader.readLine();
            if (header == null || header.isEmpty()) {
                System.err.println("Empty or invalid CSV file: " + filePath);
                return null;
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
                    products.add(product);

                } else {
                    System.err.println("Invalid data format: " + line);
                }
            });
            System.out.println("Data loaded successfully from CSV. " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading data from CSV: " + e.getMessage());
        }
        return products;
    }

    public List<Discount> getDiscounts(String filePath, List<Product> products) {
        List<Discount> discounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Header line
            String header = reader.readLine();
            if (header == null || header.isEmpty()) {
                System.err.println("Empty or invalid CSV file: " + filePath);
                return null;
            }

            reader.lines().forEach(line ->{
                String[] data = line.split(",");
                if (data.length == 9) {
                    String id = data[0];
                    LocalDate startDate = LocalDate.parse(data[6]);
                    LocalDate endDate = LocalDate.parse(data[7]);
                    int discountPercentage = Integer.parseInt(data[8]);
                    Product product = products.stream()
                            .filter(p -> p.getId().equals(id))
                            .findFirst()
                            .orElse(null);
                    Discount discount = new Discount(product, startDate, endDate, discountPercentage);
                    discounts.add(discount);
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            });
            System.out.println("Data loaded successfully from CSV. " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading data from CSV: " + e.getMessage());
        }
        return discounts;
    }

    private String extractDate(String filename) {
        String[] parts = filename.replace(".csv", "").split("_");
        return parts[parts.length - 1];
    }
}
