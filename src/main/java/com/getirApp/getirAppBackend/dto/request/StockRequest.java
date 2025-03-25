package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class StockRequest {

    @NotNull(message = "Stock quantity cannot be null")
    @NotEmpty(message = "Stock quantity cannot be empty")
    private int quantity;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private int productId;

    public StockRequest() {
    }

    public StockRequest(int quantity, LocalDateTime updatedAt, int productId) {
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
