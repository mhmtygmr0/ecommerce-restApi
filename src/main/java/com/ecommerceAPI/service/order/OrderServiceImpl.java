package com.ecommerceAPI.service.order;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.repository.OrderRepository;
import com.ecommerceAPI.service.address.AddressService;
import com.ecommerceAPI.service.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final AddressService addressService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, AddressService addressService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    public Order save(Order order) {
        order.setId(null);
        this.userService.getById(order.getUser().getId());
        this.addressService.getById(order.getAddress().getId());
        order.setTotalPrice(order.getUser().getBasket().getTotalPrice());
        return this.orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Order"));
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Order update(Order order) {
        this.getById(order.getId());
        return this.orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        Order order = this.getById(id);
        this.orderRepository.delete(order);
    }
}
