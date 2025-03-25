package com.getirApp.getirAppBackend.service.order;

import com.getirApp.getirAppBackend.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);

    Order get(long id);

    List<Order> getAll();

    Order update(Order order);

    void delete(long id);
}
