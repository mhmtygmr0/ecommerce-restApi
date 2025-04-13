package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.Min;
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
public class BasketItemRequest {

    @NotNull(message = Msg.BASKET_ID_NULL)
    private Long basketId;

    @NotNull(message = Msg.BASKET_PRODUCT_ID_NULL)
    private Long productId;

    @NotNull(message = Msg.BASKET_QUANTITY_NULL)
    @Min(value = 1, message = Msg.QUANTITY)
    private Long quantity;
}
