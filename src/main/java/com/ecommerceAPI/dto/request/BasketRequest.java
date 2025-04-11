package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketRequest {

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Address id cannot be null")
    private Long addressId;
}
