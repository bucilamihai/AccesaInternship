package com.example.price_comparator.init;

import com.example.price_comparator.domain.Catalog;
import com.example.price_comparator.domain.Discount;
import com.example.price_comparator.domain.PricedProduct;
import com.example.price_comparator.domain.Product;
import com.example.price_comparator.service.CatalogService;
import com.example.price_comparator.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class CatalogCsvLoader {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ProductService productService;

    public void loadCatalogs(String folderPath, String shopName) {
        File folder = new File(folderPath);
        File[] csvFiles = folder.listFiles((dir, name) -> name.endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            System.err.println("No CSV files found in: " + folderPath);
            return;
        }

        Map<String, File> productFiles = new HashMap<>();
        Map<String, File> discountFiles = new HashMap<>();

        for (File file : csvFiles) {
            String dateKey = extractDate(file.getName());
            if (file.getName().toLowerCase().contains("discounts"))
                discountFiles.put(dateKey, file);
            else
                productFiles.put(dateKey, file);
        }

        for (String dateKey : productFiles.keySet()) {
            File productFile = productFiles.get(dateKey);
            File discountFile = discountFiles.get(dateKey);

            LocalDate catalogDate = LocalDate.parse(dateKey);

            Catalog catalog = new Catalog();
            catalog.setShopName(shopName);
            catalog.setCatalogDate(catalogDate);

            List<PricedProduct> pricedProducts = productFile != null
                ? getPricedProducts(productFile)
                : Collections.emptyList();
            for (PricedProduct pricedProduct : pricedProducts) {
                pricedProduct.setCatalog(catalog);
            }
            catalog.setPricedProducts(pricedProducts);

            List<Discount> discounts = discountFile != null
                ? getDiscounts(discountFile, pricedProducts)
                : Collections.emptyList();
            for (Discount discount : discounts) {
                discount.setCatalog(catalog);
            }
            catalog.setDiscounts(discounts);

            // persist everything
            catalogService.saveCatalog(catalog);
            System.out.println("Saved catalog for " + shopName + " @ " + catalogDate);
        }
    }


    private List<PricedProduct> getPricedProducts(File file) {
        List<PricedProduct> pricedProducts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();  // Skip header
            if (headerLine == null) {
                throw new IOException("CSV file is empty: " + file.getName());
            }

            reader.lines().forEach(line -> {
                String[] columns = line.split(",");
                if (columns.length != 8) {
                    System.err.println("Skipping invalid product row (expected 8 columns): " + line);
                }

                try {
                    String id = columns[0];
                    String name = columns[1];
                    String category = columns[2];
                    String brand = columns[3];
                    double quantity = Double.parseDouble(columns[4]);
                    String unit = columns[5];
                    double price = Double.parseDouble(columns[6]);
                    String currency = columns[7];

                    // find or create Product
                    Product product = productService.findProductById(id); 
                    if(product == null) {
                        product = new Product(id, name, category, brand, quantity, unit);
                        productService.saveProduct(product);
                    }

                    // create PricedProduct
                    PricedProduct pricedProduct = new PricedProduct(product, price, currency);

                    pricedProducts.add(pricedProduct);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping row due to number format error: " + line);
                }
            });
        } catch (IOException exception) {
            System.err.println("Error reading product CSV file: " + file.getAbsolutePath());
        }

        return pricedProducts;
    }

    private List<Discount> getDiscounts(File file, List<PricedProduct> pricedProducts) {
        List<Discount> discounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();  // Skip the header
            if (headerLine == null) {
                throw new IOException("Discount CSV file is empty: " + file.getName());
            }

            reader.lines().forEach(line -> {
                String[] columns = line.split(",");
                if (columns.length != 9) {
                    System.err.println("Skipping invalid discount row (expected 9 columns): " + line);
                }

                try {
                    String productId = columns[0];
                    LocalDate startDate = LocalDate.parse(columns[6]);
                    LocalDate endDate = LocalDate.parse(columns[7]);
                    int discountPercentage = Integer.parseInt(columns[8]);

                    PricedProduct pricedProduct = pricedProducts.stream()
                        .filter(p -> p.getProduct().getId().equals(productId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(
                            "Unknown product ID in discount CSV (no PricedProduct): " + productId));

                    Discount discount = new Discount();
                    discount.setPricedProduct(pricedProduct);
                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);
                    discount.setDiscountPercentage(discountPercentage);

                    discounts.add(discount);
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Skipping row due to parsing error: " + line);
                }
            });
        } catch (IOException exception) {
            System.err.println("Error reading discounts CSV file: " + file.getAbsolutePath());
        }

        return discounts;
    }

    private String extractDate(String filename) {
        String[] parts = filename.replace(".csv", "").split("_");
        return parts[parts.length - 1];
    }
}
