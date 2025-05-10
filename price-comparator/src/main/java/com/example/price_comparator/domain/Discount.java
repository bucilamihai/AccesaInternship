package com.example.price_comparator.domain;
import java.time.LocalDate;

public class Discount {
    private Product product;
    private LocalDate startDate;
    private LocalDate endDate;
    private int discountPercentage;

    public Discount(Product product, LocalDate startDate, LocalDate endDate, int discountPercentage) {
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "product=" + product +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;

        Discount discount = (Discount) o;

        if (discountPercentage != discount.discountPercentage) return false;
        if (!product.equals(discount.product)) return false;
        if (!startDate.equals(discount.startDate)) return false;
        return endDate.equals(discount.endDate);
    }

    @Override
    public int hashCode() {
        int result = product.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + discountPercentage;
        return result;
    }
}
