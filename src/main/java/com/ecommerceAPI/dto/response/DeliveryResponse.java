package com.ecommerceAPI.dto.response;

import com.ecommerceAPI.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {
    private Long id;
    private LocalDateTime assignedAt;
    private LocalDateTime deliveredAt;
    private DeliveryStatus status;
    private Long orderId;
    private Long courierId;
}
