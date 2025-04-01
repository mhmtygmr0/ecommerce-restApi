package com.ecommerceAPI.dto.response;

public class BasketItemResponse {

    private Long id;
    private Long basketId;
    private Long productId;
    private int quantity;
    private double price;

    public BasketItemResponse() {
    }

    public BasketItemResponse(Long id, Long basketId, Long productId, int quantity, double price) {
        this.id = id;
        this.basketId = basketId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
