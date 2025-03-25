package com.getirApp.getirAppBackend.dto.response;

public class AddressResponse {

    private Long id;
    private String title;
    private String street;
    private String city;
    private String district;
    private String postalCode;
    private Long userId;

    public AddressResponse() {
    }

    public AddressResponse(Long id, String title, String street, String city, String district, String postalCode, Long userId) {
        this.id = id;
        this.title = title;
        this.street = street;
        this.city = city;
        this.district = district;
        this.postalCode = postalCode;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
