package com.ecommerceAPI.dto.response;

import com.ecommerceAPI.enums.CourierStatus;
import com.ecommerceAPI.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private UserRole role;
    private CourierStatus courierStatus;
    private String imageUrl;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdAt;
}