package com.ecommerceAPI.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long basketId;
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Double productPrice;
    private Long quantity;
    private Double totalPrice;
}
