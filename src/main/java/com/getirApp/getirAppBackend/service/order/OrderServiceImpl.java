package com.getirApp.getirAppBackend.service.order;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.Order;
import com.getirApp.getirAppBackend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Order get(long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Order update(Order order) {
        this.get(order.getId());
        return this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Order order = this.get(id);
        this.orderRepository.delete(order);
    }
}
