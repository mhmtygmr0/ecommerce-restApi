package com.ecommerceAPI.dto.request;

import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = Msg.USERNAME_NULL)
    @NotEmpty(message = Msg.USERNAME_EMPTY)
    private String name;

    @NotNull(message = Msg.SURNAME_NULL)
    @NotEmpty(message = Msg.SURNAME_EMPTY)
    private String surname;

    @NotNull(message = Msg.BIRTH_DATE_NULL)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;

    @Email(message = Msg.EMAIL)
    @NotBlank(message = Msg.EMAIL_NULL)
    private String email;

    @NotNull(message = Msg.PASSWORD_NULL)
    @NotEmpty(message = Msg.PASSWORD_EMPTY)
    private String password;

    @NotNull(message = Msg.PHONE_NULL)
    @Pattern(regexp = "^[0-9]{11}$", message = Msg.PHONE)
    private String phone;

    private UserRole role;
}