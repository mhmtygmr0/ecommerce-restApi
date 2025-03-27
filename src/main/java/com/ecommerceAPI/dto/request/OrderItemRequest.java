package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {

    @NotNull(message = "Order id cannot be null")
    private Long orderId;

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
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
