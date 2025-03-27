package com.getirApp.getirAppBackend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.getirApp.getirAppBackend.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Double totalPrice;
    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    private Long userId;
    private Long addressId;
    private List<OrderItemResponse> orderItems;

    public OrderResponse() {
    }

    public OrderResponse(Long id, Double totalPrice, OrderStatus status, LocalDateTime createdAt, Long userId, Long addressId, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.addressId = addressId;
        this.orderItems = orderItems;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemResponse> orderItems) {
        this.orderItems = orderItems;
    }
}
