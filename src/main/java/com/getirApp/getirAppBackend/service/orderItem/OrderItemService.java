package com.getirApp.getirAppBackend.service.orderItem;

import com.getirApp.getirAppBackend.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem get(long id);

    List<OrderItem> getAll();

    OrderItem update(OrderItem orderItem);

    void delete(long id);
}
