package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product name cannot be null")
    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    private String imageUrl;

    @NotNull(message = "Product category id cannot be null")
    private int categoryId;

    @NotNull(message = "Product stock quantity cannot be null")
    @Min(value = 0, message = "Product stock quantity cannot be negative")
    private Long stockQuantity;
}
