package com.ecommerceAPI.service.orderItem;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.OrderItem;
import com.ecommerceAPI.repository.OrderItemRepository;
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
        orderItem.setId(null);
        orderItem.setProductName(orderItem.getProduct().getName());
        orderItem.setProductPrice(orderItem.getProduct().getPrice());
        orderItem.setProductImageUrl(orderItem.getProduct().getImageUrl());
        return this.orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getById(Long id) {
        return this.orderItemRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "OrderItem"));
    }

    @Override
    public List<OrderItem> getAll() {
        return this.orderItemRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public OrderItem update(OrderItem orderItem) {
        return this.orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        OrderItem orderItem = this.getById(id);
        this.orderItemRepository.delete(orderItem);
    }
}
