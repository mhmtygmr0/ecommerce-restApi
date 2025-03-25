package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {

    @NotNull(message = "Order id cannot be null")
    @NotEmpty(message = "Order id cannot be empty")
    private Long orderId;

    @NotNull(message = "Product id cannot be null")
    @NotEmpty(message = "Product id cannot be empty")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @NotEmpty(message = "Quantity cannot be empty")
    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;

    public OrderItemRequest() {
    }

    public OrderItemRequest(Long orderId, Long productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
