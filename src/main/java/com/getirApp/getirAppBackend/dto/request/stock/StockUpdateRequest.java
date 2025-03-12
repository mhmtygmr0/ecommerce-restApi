package com.getirApp.getirAppBackend.dto.request.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class StockUpdateRequest {

    @NotNull(message = "Stock quantity cannot be null")
    private int quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public StockUpdateRequest() {
    }

    public StockUpdateRequest(int quantity, LocalDateTime updatedAt) {
        this.quantity = quantity;
        this.updatedAt = updatedAt;
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
}
