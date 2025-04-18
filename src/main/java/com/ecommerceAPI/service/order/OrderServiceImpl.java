package com.ecommerceAPI.service.order;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Address;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.repository.OrderRepository;
import com.ecommerceAPI.service.address.AddressService;
import com.ecommerceAPI.service.basket.BasketService;
import com.ecommerceAPI.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final BasketService basketService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, AddressService addressService, BasketService basketService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.basketService = basketService;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        order.setId(null);

        User user = this.userService.getById(order.getUser().getId());
        Address address = this.addressService.getById(order.getAddress().getId());
        Basket basket = this.basketService.getById(user.getBasket().getId());

        this.addressService.checkAddressBelongsToUser(address.getId(), user.getId());

        order.setUser(user);
        order.setAddress(address);
        order.setTotalPrice(basket.getTotalPrice());

        return this.orderRepository.save(order);
    }


    @Override
    public Order getById(Long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Order update(Order order) {
        this.getById(order.getId());

        User user = this.userService.getById(order.getUser().getId());
        Address address = this.addressService.getById(order.getAddress().getId());

        this.addressService.checkAddressBelongsToUser(address.getId(), user.getId());

        order.setUser(user);
        order.setAddress(address);

        return this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = this.getById(id);
        this.orderRepository.delete(order);
    }
}
