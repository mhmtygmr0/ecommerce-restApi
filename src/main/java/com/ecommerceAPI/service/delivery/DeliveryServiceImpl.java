package com.ecommerceAPI.service.delivery;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Delivery;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.CourierStatus;
import com.ecommerceAPI.enums.DeliveryStatus;
import com.ecommerceAPI.repository.DeliveryRepository;
import com.ecommerceAPI.service.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserService userService;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, @Lazy UserService userService) {
        this.deliveryRepository = deliveryRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Delivery save(Delivery delivery) {
        delivery.setId(null);
        return this.deliveryRepository.save(delivery);
    }

    @Override
    public Delivery getById(Long id) {
        return this.deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Delivery"));
    }

    @Override
    public List<Delivery> getAll() {
        return this.deliveryRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Delivery update(Delivery delivery) {
        this.getById(delivery.getId());
        return this.deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Delivery delivery = this.getById(id);
        this.deliveryRepository.delete(delivery);
    }

    @Override
    @Transactional
    public void assignCourierToOrder(Order order) {
        List<User> availableCouriers = this.userService.findAvailableCouriers();

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setAssignedAt(LocalDateTime.now());

        if (availableCouriers.isEmpty()) {
            delivery.setStatus(DeliveryStatus.PENDING);
        } else {
            User courier = availableCouriers.get(0);
            delivery.setCourier(courier);
            delivery.setStatus(DeliveryStatus.ASSIGNED);
            this.userService.updateCourierStatus(courier.getId(), CourierStatus.BUSY);
        }

        this.deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void markAsDelivered(Long deliveryId) {
        Delivery delivery = this.getById(deliveryId);

        if (delivery.getStatus() == DeliveryStatus.DELIVERED) {
            throw new IllegalStateException(Msg.DELIVERY_ALREADY_MARKED);
        }

        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setDeliveredAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        if (delivery.getCourier() != null) {
            Long courierId = delivery.getCourier().getId();
            this.userService.updateCourierStatus(courierId, CourierStatus.AVAILABLE);
            this.assignAvailableCourierToPendingDelivery();
        }

        this.deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void assignAvailableCourierToPendingDelivery() {
        List<Delivery> pendingDeliveries = this.deliveryRepository.findByStatusOrderByAssignedAtAsc(DeliveryStatus.PENDING);
        List<User> availableCouriers = this.userService.findAvailableCouriers();

        if (!pendingDeliveries.isEmpty() && !availableCouriers.isEmpty()) {
            Delivery pendingDelivery = pendingDeliveries.get(0);
            User availableCourier = availableCouriers.get(0);

            pendingDelivery.setCourier(availableCourier);
            pendingDelivery.setStatus(DeliveryStatus.ASSIGNED);
            pendingDelivery.setAssignedAt(LocalDateTime.now());

            this.userService.updateCourierStatus(availableCourier.getId(), CourierStatus.BUSY);
            this.deliveryRepository.save(pendingDelivery);
        }
    }
}