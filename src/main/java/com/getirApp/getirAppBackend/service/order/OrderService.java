package com.getirApp.getirAppBackend.service.order;

import com.getirApp.getirAppBackend.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order, Long userId, Long addressId);

    Order get(long id);

    List<Order> getAll();

    Order update(Order order, Long userId, Long addressId);

    void delete(long id);
}
