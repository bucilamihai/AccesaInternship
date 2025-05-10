package com.example.price_comparator.domain;

import java.util.List;
import java.time.LocalDate;

public class Catalog {
    private String shopName;
    private LocalDate startDate;
    private List<Product> products;
    private List<Discount> discounts;

    public Catalog(String shopName, LocalDate startDate, List<Product> products, List<Discount> discounts) {
        this.shopName = shopName;
        this.startDate = startDate;
        this.products = products;
        this.discounts = discounts;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }   

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "shopName=" + shopName +
                ", startDate=" + startDate +
                ", products=" + products +
                ", discounts=" + discounts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Catalog)) return false;

        Catalog catalog = (Catalog) o;

        if (!shopName.equals(catalog.shopName)) return false;
        if (!startDate.equals(catalog.startDate)) return false;
        if (!products.equals(catalog.products)) return false;
        return discounts.equals(catalog.discounts);
    }

    @Override
    public int hashCode() {
        int result = shopName.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + products.hashCode();
        result = 31 * result + discounts.hashCode();
        return result;
    }
}
