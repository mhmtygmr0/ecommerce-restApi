package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BasketItemRequest {

    @NotNull(message = "Basket id cannot be null")
    private Long basketId;

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;

    public BasketItemRequest() {
    }

    public BasketItemRequest(Long basketId, Long productId, Long quantity) {
        this.basketId = basketId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
