package com.ecommerceAPI.enums;

public enum OrderStatus {
    PENDING,    // Sipariş alındı, hazırlanıyor
    SHIPPED,    // Sipariş kargoya verildi
    DELIVERED,  // Sipariş teslim edildi
    CANCELLED   // Sipariş iptal edildi
}
