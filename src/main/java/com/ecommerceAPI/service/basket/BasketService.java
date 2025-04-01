package com.ecommerceAPI.service.basket;

import com.ecommerceAPI.entity.Basket;

import java.util.List;

public interface BasketService {
    Basket save(Basket basket);

    Basket getById(Long id);

    List<Basket> getAll();

    Basket update(Basket basket);

    void delete(Long id);
}
