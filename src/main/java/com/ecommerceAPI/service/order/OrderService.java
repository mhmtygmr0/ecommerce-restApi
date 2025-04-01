package com.ecommerceAPI.service.order;

import com.ecommerceAPI.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);

    Order getById(Long id);

    List<Order> getAll();

    Order update(Order order);

    void delete(Long id);
}
