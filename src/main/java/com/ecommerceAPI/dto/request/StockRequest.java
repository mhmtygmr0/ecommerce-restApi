package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {

    @NotNull(message = "Stock quantity cannot be null")
    private Long quantity;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Long productId;
}
