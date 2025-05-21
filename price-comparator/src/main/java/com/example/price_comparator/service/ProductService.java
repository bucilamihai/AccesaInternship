package com.example.price_comparator.service;
import com.example.price_comparator.domain.Product;
import com.example.price_comparator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product findProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }
}
