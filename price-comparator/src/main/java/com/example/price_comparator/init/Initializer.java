package com.example.price_comparator.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {
   
    @Autowired
    private CatalogCsvLoader csvCatalogLoader;

    private final String folderPath = "src/main/resources/shops";

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        List<String> shopNames = loadShops(folderPath);
        shopNames.forEach(shopName -> {
            csvCatalogLoader.loadCatalogs(folderPath + "/" + shopName, shopName);
        });
    }

    public List<String> loadShops(String folderPath) {
        List<String> shopNames = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles(File::isDirectory);
        if (files != null) {
            for (File file : files) {
                // First letter uppercase and the rest lowercase
                String shopName = file.getName().substring(0, 1).toUpperCase() 
                    + file.getName().substring(1).toLowerCase();
                shopNames.add(shopName);
            }
        } else {
            System.err.println("No folders found in the path: " + folderPath);
        }

        return shopNames;
    }
}
