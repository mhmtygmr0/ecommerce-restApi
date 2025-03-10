package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CategorySaveRequest {
    @NotNull(message = "Category name cannot be null")
    @NotEmpty(message = "Category name cannot be empty")
    private String name;

    public CategorySaveRequest() {
    }

    public CategorySaveRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
