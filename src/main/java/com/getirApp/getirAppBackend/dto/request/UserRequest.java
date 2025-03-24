package com.getirApp.getirAppBackend.dto.request;

import com.getirApp.getirAppBackend.entity.UserRole;
import jakarta.validation.constraints.*;

public class UserRequest {

    @NotNull(message = "Kullanıcı adı boş olamaz")
    @NotEmpty(message = "Kullanıcı adı boş olamaz")
    private String name;

    @Email(message = "Geçersiz e-posta biçimi")
    @NotBlank(message = "E-posta boş olamaz")
    private String email;

    @NotNull(message = "Şifre boş olamaz")
    @NotEmpty(message = "Şifre boş olamaz")
    private String password;

    @Pattern(regexp = "^[0-9]{11}$", message = "Telefon numarası 11 haneli olmalıdır")
    private String phone;

    private UserRole role;

    public UserRequest() {
    }

    public UserRequest(String name, String email, String password, String phone, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}