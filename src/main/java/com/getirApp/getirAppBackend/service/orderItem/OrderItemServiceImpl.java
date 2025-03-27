package com.getirApp.getirAppBackend.service.orderItem;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.OrderItem;
import com.getirApp.getirAppBackend.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderItem save(OrderItem orderItem) {
        return this.orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getById(long id) {
        return this.orderItemRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<OrderItem> getAll() {
        return this.orderItemRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public OrderItem update(OrderItem orderItem) {
        this.getById(orderItem.getId());
        return this.orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public void delete(long id) {
        OrderItem orderItem = this.getById(id);
        this.orderItemRepository.delete(orderItem);
    }
}
