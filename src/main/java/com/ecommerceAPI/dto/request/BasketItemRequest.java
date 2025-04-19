package com.ecommerceAPI.dto.request;

import com.ecommerceAPI.core.utils.Msg;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemRequest {

    @NotNull(message = Msg.BASKET_ITEM_ID_NULL)
    private Long basketId;

    @NotNull(message = Msg.BASKET_ITEM_PRODUCT_ID_NULL)
    private Long productId;

    @NotNull(message = Msg.BASKET_ITEM_QUANTITY_NULL)
    @Min(value = 1, message = Msg.QUANTITY)
    private Long quantity;
}
