package com.getirApp.getirAppBackend.service.orderItem;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.Order;
import com.getirApp.getirAppBackend.entity.OrderItem;
import com.getirApp.getirAppBackend.entity.Product;
import com.getirApp.getirAppBackend.entity.Stock;
import com.getirApp.getirAppBackend.repository.OrderItemRepository;
import com.getirApp.getirAppBackend.repository.OrderRepository;
import com.getirApp.getirAppBackend.repository.ProductRepository;
import com.getirApp.getirAppBackend.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, StockRepository stockRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderItem save(OrderItem orderItem) {
        Order order = orderRepository.findById(orderItem.getOrder().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Order")));

        Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Product")));

        if (product.getStock() == null) {
            throw new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Stock for product ID " + product.getId()));
        }

        Stock stock = stockRepository.findById(product.getStock().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Stock")));

        if (stock.getQuantity() < orderItem.getQuantity()) {
            throw new IllegalArgumentException(Msg.INSUFFICIENT_STOCK);
        }

        stock.setQuantity(stock.getQuantity() - orderItem.getQuantity());
        stockRepository.save(stock);

        double newTotalPrice = order.getTotalPrice() + orderItem.getPrice();
        order.setTotalPrice(newTotalPrice);
        orderRepository.save(order);

        orderItem.setOrder(order);

        return orderItemRepository.save(orderItem);
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
        OrderItem existingOrderItem = this.getById(orderItem.getId());

        Order order = orderRepository.findById(existingOrderItem.getOrder().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Order")));

        Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Product")));

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        Stock stock = stockRepository.findById(product.getStock().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Stock")));

        order.setTotalPrice(order.getTotalPrice() - existingOrderItem.getPrice());

        int quantityDiff = orderItem.getQuantity() - existingOrderItem.getQuantity();
        if (quantityDiff > 0) {

            if (stock.getQuantity() < quantityDiff) {
                throw new IllegalArgumentException(Msg.INSUFFICIENT_STOCK);
            }
            stock.setQuantity(stock.getQuantity() - quantityDiff);
        } else if (quantityDiff < 0) {

            stock.setQuantity(stock.getQuantity() + Math.abs(quantityDiff));
        }
        stockRepository.save(stock);

        order.setTotalPrice(order.getTotalPrice() + orderItem.getPrice());
        orderRepository.save(order);

        return orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public void delete(long id) {
        OrderItem orderItem = this.getById(id);

        Order order = orderRepository.findById(orderItem.getOrder().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Order")));

        Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Product")));

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        Stock stock = stockRepository.findById(product.getStock().getId())
                .orElseThrow(() -> new NotFoundException(String.format(Msg.NOT_FOUND_ENTITY, "Stock")));

        stock.setQuantity(stock.getQuantity() + orderItem.getQuantity());
        stockRepository.save(stock);

        order.setTotalPrice(order.getTotalPrice() - orderItem.getPrice());
        orderRepository.save(order);

        this.orderItemRepository.delete(orderItem);
    }

}
