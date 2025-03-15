package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class StockRequest {

    @NotNull(message = "Stock quantity cannot be null")
    private int quantity;

    private int productId;

    public StockRequest() {
    }

    public StockRequest(int quantity, int productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
