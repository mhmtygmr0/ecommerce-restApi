package com.getirApp.getirAppBackend.dto.response;

import com.getirApp.getirAppBackend.entity.Product;

import java.util.List;

public class CategoryResponse {
    private int id;
    private String name;
    private String imageUrl;
    private List<Product> productList;

    public CategoryResponse() {
    }

    public CategoryResponse(int id, String name, String imageUrl, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.productList = productList;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
