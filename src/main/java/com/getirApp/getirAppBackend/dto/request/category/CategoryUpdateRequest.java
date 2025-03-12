package com.getirApp.getirAppBackend.dto.request.category;

public class CategoryUpdateRequest {

    private String name;

    private String imageUrl;

    public CategoryUpdateRequest() {
    }

    public CategoryUpdateRequest(String name, String imageUrl) {
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
