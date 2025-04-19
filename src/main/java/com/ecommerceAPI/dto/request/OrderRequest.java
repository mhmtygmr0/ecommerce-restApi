package com.ecommerceAPI.dto.request;

import com.ecommerceAPI.core.utils.Msg;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = Msg.ORDER_USER_ID_NULL)
    private Long userId;

    @NotNull(message = Msg.ORDER_ADDRESS_ID_NULL)
    private Long addressId;
}
