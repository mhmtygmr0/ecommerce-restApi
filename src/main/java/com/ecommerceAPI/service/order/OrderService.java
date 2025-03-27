package com.ecommerceAPI.service.order;

import com.ecommerceAPI.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order, Long userId, Long addressId);

    Order getById(long id);

    List<Order> getAll();

    Order update(Order order, Long userId, Long addressId);

    void delete(long id);
}
