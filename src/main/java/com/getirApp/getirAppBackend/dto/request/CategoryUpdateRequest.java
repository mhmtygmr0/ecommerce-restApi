package com.getirApp.getirAppBackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CategoryUpdateRequest {

    private int id;

    @NotNull(message = "Category name cannot be null")
    @NotEmpty(message = "Category name cannot be empty")
    private String name;

    public CategoryUpdateRequest() {
    }

    public CategoryUpdateRequest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
