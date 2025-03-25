package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddressRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "District cannot be blank")
    private String district;

    @NotBlank(message = "Postal code cannot be blank")
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
