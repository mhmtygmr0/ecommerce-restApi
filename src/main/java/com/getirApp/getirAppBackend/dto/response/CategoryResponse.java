package com.getirApp.getirAppBackend.dto.response;

import java.util.List;

public class CategoryResponse {
    private int id;
    private String name;
    private String imageUrl;
    private List<ProductResponse> productList;

    public CategoryResponse(int id, String name, String imageUrl, List<ProductResponse> productList) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.productList = productList;
    }

    public CategoryResponse() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ProductResponse> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductResponse> productList) {
        this.productList = productList;
    }
}
