package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotNull(message = "Product name cannot be null")
    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    private String imageUrl;

    @NotNull(message = "Product category id cannot be null")
    private int categoryId;

    @NotNull(message = "Product stock quantity cannot be null")
    @Min(value = 0, message = "Product stock quantity cannot be negative")
    private int stockQuantity;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Double price, String imageUrl, int categoryId, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
