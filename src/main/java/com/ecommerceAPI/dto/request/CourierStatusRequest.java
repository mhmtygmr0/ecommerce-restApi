package com.ecommerceAPI.dto.request;

import com.ecommerceAPI.enums.CourierStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourierStatusRequest {
    @NotNull(message = "Status cannot be null")
    private CourierStatus status;
}