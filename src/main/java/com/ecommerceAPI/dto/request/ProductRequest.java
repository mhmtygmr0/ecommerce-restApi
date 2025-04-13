package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
public class ProductRequest {

    @NotNull(message = Msg.PRODUCT_NAME_NULL)
    @NotEmpty(message = Msg.PRODUCT_NAME_EMPTY)
    private String name;

    private String description;

    @NotNull(message = Msg.PRODUCT_PRICE_NULL)
    @Min(value = 0, message = Msg.PRICE)
    private Double price;

    private String imageUrl;

    @NotNull(message = Msg.PRODUCT_CATEGORY_ID_NULL)
    private Long categoryId;

    @NotNull(message = Msg.PRODUCT_STOCK_NULL)
    @Min(value = 0, message = Msg.QUANTITY)
    private Long stockQuantity;
}
