package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Address id cannot be null")
    private Long addressId;

    public OrderRequest() {
    }

    public OrderRequest(Long userId, Long addressId) {
        this.userId = userId;
        this.addressId = addressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
