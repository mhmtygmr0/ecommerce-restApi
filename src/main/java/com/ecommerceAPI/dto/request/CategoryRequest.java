package com.ecommerceAPI.dto.request;

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
public class CategoryRequest {
    @NotNull(message = Msg.CATEGORY_NAME_NULL)
    @NotEmpty(message = Msg.CATEGORY_NAME_EMPTY)
    private String name;

    private String imageUrl;
}
