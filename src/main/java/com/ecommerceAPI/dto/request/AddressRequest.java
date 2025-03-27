package com.ecommerceAPI.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
    @Pattern(regexp = "^[0-9]{5}$", message = "Postal code must be exactly 5 digits")
    private String postalCode;

    private Long userId;

    public AddressRequest() {
    }

    public AddressRequest(String title, String street, String city, String district, String postalCode, Long userId) {
        this.title = title;
        this.street = street;
        this.city = city;
        this.district = district;
        this.postalCode = postalCode;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
