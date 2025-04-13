package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ecommerceAPI.core.utils.Msg;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketRequest {

    @NotNull(message = Msg.BASKET_USER_ID_NULL)
    private Long userId;

    @NotNull(message = Msg.BASKET_ADDRESS_ID_NULL)
    private Long addressId;
}
