package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotNull(message = "Ürün adı boş olamaz")
    @NotEmpty(message = "Ürün adı boş olamaz")
    private String name;

    private String description;

    @NotNull(message = "Fiyat boş olamaz")
    @Min(value = 0, message = "Fiyat negatif olamaz")
    private Double price;

    private String imageUrl;

    @NotNull(message = "Kategori ID boş olamaz")
    private int categoryId;

    @NotNull(message = "Stok miktarı boş olamaz")
    @Min(value = 0, message = "Stok miktarı negatif olamaz")
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
