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

    @NotNull(message = Msg.ADDRESS_TITLE_NULL)
    @NotEmpty(message = Msg.ADDRESS_TITLE_EMPTY)
    private String title;

    @NotNull(message = Msg.ADDRESS_STREET_NULL)
    @NotEmpty(message = Msg.ADDRESS_STREET_EMPTY)
    private String street;

    @NotNull(message = Msg.ADDRESS_CITY_NULL)
    @NotEmpty(message = Msg.ADDRESS_CITY_EMPTY)
    private String city;

    @NotNull(message = Msg.ADDRESS_DISTRICT_NULL)
    @NotEmpty(message = Msg.ADDRESS_DISTRICT_EMPTY)
    private String district;

    @NotNull(message = Msg.ADDRESS_POSTAL_CODE_NULL)
    @NotEmpty(message = Msg.ADDRESS_POSTAL_CODE_EMPTY)
    @Pattern(regexp = "^[0-9]{5}$", message = Msg.POSTAL_CODE)
    private String postalCode;

    private Long userId;
}
