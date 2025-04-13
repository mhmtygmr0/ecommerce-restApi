package com.ecommerceAPI.dto.request;

import com.ecommerceAPI.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ecommerceAPI.core.utils.Msg;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = Msg.USERNAME_NULL)
    @NotEmpty(message = Msg.USERNAME_EMPTY)
    private String name;

    @Email(message = Msg.EMAIL)
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotNull(message = Msg.PASSWORD_NULL)
    @NotEmpty(message = Msg.PASSWORD_EMPTY)
    private String password;

    @NotNull(message = Msg.PHONE_NULL)
    @Pattern(regexp = "^[0-9]{11}$", message = Msg.PHONE)
    private String phone;

    private UserRole role;
}