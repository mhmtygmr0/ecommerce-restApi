package com.ecommerceAPI.dto.request;

import com.ecommerceAPI.core.utils.Msg;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = "Address title cannot be null")
    @NotEmpty(message = "Address title cannot be empty")
    private String title;

    @NotNull(message = "Address street cannot be null")
    @NotEmpty(message = "Address street cannot be empty")
    private String street;

    @NotNull(message = "Address city cannot be null")
    @NotEmpty(message = "Address city cannot be empty")
    private String city;

    @NotNull(message = "Address district cannot be null")
    @NotEmpty(message = "Address district cannot be empty")
    private String district;

    @NotNull(message = "Address postal code cannot be null")
    @NotEmpty(message = "Address postalCode cannot be empty")
    @Pattern(regexp = "^[0-9]{5}$", message = Msg.POSTAL_CODE)
    private String postalCode;

    private Long userId;
}
