package com.ecommerceAPI.service.order;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Address;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.repository.OrderRepository;
import com.ecommerceAPI.service.address.AddressService;
import com.ecommerceAPI.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, AddressService addressService, UserService userService) {
        this.orderRepository = orderRepository;
        this.addressService = addressService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        Long userId = order.getUser().getId();
        Long addressId = order.getAddress().getId();
        User user = this.userService.getById(userId);
        Address address = this.addressService.getById(addressId);

        if (!addressService.doesAddressBelongToUser(addressId, userId)) {
            throw new NotFoundException(Msg.NOT_FOUND, "Address");
        }

        order.setUser(user);
        order.setAddress(address);
        order.setTotalPrice(order.getTotalPrice() != null ? order.getTotalPrice() : 0.0); // Null kontrolü

        return this.orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Order"));
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Order update(Order order) {
        Order existingOrder = this.getById(order.getId());
        Long userId = order.getUser().getId();
        Long addressId = order.getAddress().getId();

        User user = this.userService.getById(userId);
        Address address = this.addressService.getById(addressId);

        if (!this.addressService.doesAddressBelongToUser(addressId, userId)) {
            throw new NotFoundException(Msg.NOT_FOUND, "Address");
        }

        order.setUser(user);
        order.setAddress(address);

        order.setTotalPrice(order.getTotalPrice() != null ? order.getTotalPrice() : existingOrder.getTotalPrice());
        order.setCreatedAt(order.getCreatedAt() != null ? order.getCreatedAt() : existingOrder.getCreatedAt());
        order.setStatus(order.getStatus() != null ? order.getStatus() : existingOrder.getStatus());

        return this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = getById(id);
        this.orderRepository.delete(order);
    }
}
