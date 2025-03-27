package com.ecommerceAPI.service.order;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Address;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.repository.AddressRepository;
import com.ecommerceAPI.repository.OrderRepository;
import com.ecommerceAPI.repository.UserRepository;
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
            throw new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Address"));
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
            throw new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Address"));
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
