package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class StockRequest {

    @NotNull(message = "Stock quantity cannot be null")
    private Long quantity;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Long productId;

    public StockRequest() {
    }

    public StockRequest(Long quantity, LocalDateTime updatedAt, Long productId) {
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
