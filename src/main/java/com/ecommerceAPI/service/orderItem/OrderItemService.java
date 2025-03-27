package com.ecommerceAPI.service.orderItem;

import com.ecommerceAPI.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem getById(long id);

    List<OrderItem> getAll();

    OrderItem update(OrderItem orderItem);

    void delete(long id);
}
