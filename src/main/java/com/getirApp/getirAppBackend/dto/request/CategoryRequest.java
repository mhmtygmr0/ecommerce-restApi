package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CategoryRequest {
    @NotNull(message = "Kategori adı boş olamaz")
    @NotEmpty(message = "Kategori adı boş olamaz")
    private String name;

    private String imageUrl;

    public CategoryRequest() {
    }

    public CategoryRequest(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
