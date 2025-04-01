package com.ecommerceAPI.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class BasketResponse {
    private Long id;
    private Double totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    private Long userId;
    private Long addressId;
    private List<BasketItemResponse> basketItems;

    public BasketResponse() {
    }

    public BasketResponse(Long id, Double totalPrice, LocalDateTime createdAt, Long userId, Long addressId, List<BasketItemResponse> basketItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.userId = userId;
        this.addressId = addressId;
        this.basketItems = basketItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public List<BasketItemResponse> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItemResponse> basketItems) {
        this.basketItems = basketItems;
    }
}
