package com.getirApp.getirAppBackend.dto.request.stock;

import jakarta.validation.constraints.NotNull;

public class StockSaveRequest {

    @NotNull(message = "Stock quantity cannot be null")
    private int quantity;

    public StockSaveRequest() {
    }

    public StockSaveRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
