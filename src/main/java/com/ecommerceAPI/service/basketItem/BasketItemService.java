package com.ecommerceAPI.service.basketItem;

import com.ecommerceAPI.entity.BasketItem;

import java.util.List;

public interface BasketItemService {
    BasketItem save(BasketItem basketItem);

    BasketItem getById(Long id);

    List<BasketItem> getAll();

    List<BasketItem> getBasketItemsByBasketId(Long basketId);

    BasketItem update(BasketItem basketItem);

    void delete(Long id);

    void deleteByBasketId(Long basketId);
}
