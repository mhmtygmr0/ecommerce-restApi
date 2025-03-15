package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class StockRequest {

    @NotNull(message = "Stock quantity cannot be null")
    private int quantity;

    private int productId;

    public StockRequest() {
    }

    public StockRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
