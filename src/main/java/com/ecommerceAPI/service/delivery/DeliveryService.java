package com.ecommerceAPI.service.delivery;

import com.ecommerceAPI.entity.Delivery;
import com.ecommerceAPI.entity.Order;

import java.util.List;

public interface DeliveryService {
    Delivery save(Delivery delivery);

    Delivery getById(Long id);

    List<Delivery> getAll();

    Delivery update(Delivery delivery);

    void delete(Long id);

    void assignCourierToOrder(Order order);

    void markAsDelivered(Long deliveryId);

    void assignAvailableCourierToPendingDelivery();
}
