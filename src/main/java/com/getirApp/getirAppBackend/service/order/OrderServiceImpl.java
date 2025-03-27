package com.getirApp.getirAppBackend.service.order;

import com.getirApp.getirAppBackend.core.exception.ForbiddenException;
import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.Address;
import com.getirApp.getirAppBackend.entity.Order;
import com.getirApp.getirAppBackend.entity.User;
import com.getirApp.getirAppBackend.repository.AddressRepository;
import com.getirApp.getirAppBackend.repository.OrderRepository;
import com.getirApp.getirAppBackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order save(Order order, Long userId, Long addressId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "User")));

        if (!this.addressRepository.existsByIdAndUserId(addressId, userId)) {
            throw new ForbiddenException(Msg.FORBIDDEN);
        }

        Address address = this.addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Address")));

        order.setUser(user);
        order.setAddress(address);

        order.setTotalPrice(0.0);

        return this.orderRepository.save(order);
    }


    @Override
    public Order getById(long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Order update(Order order, Long userId, Long addressId) {
        Order existingOrder = this.getById(order.getId());

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "User")));

        if (!this.addressRepository.existsByIdAndUserId(addressId, userId)) {
            throw new ForbiddenException(Msg.FORBIDDEN);
        }

        Address address = this.addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Address")));

        order.setUser(user);
        order.setAddress(address);

        if (order.getTotalPrice() == null) {
            order.setTotalPrice(existingOrder.getTotalPrice());
        }

        if (order.getCreatedAt() == null) {
            order.setCreatedAt(existingOrder.getCreatedAt());
        }

        if (order.getStatus() == null) {
            order.setStatus(existingOrder.getStatus());
        }

        return this.orderRepository.save(order);
    }


    @Override
    @Transactional
    public void delete(long id) {
        Order order = this.getById(id);
        this.orderRepository.delete(order);
    }

}
