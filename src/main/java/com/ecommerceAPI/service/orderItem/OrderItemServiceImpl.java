package com.ecommerceAPI.service.orderItem;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.entity.OrderItem;
import com.ecommerceAPI.entity.Product;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.repository.OrderItemRepository;
import com.ecommerceAPI.repository.OrderRepository;
import com.ecommerceAPI.repository.ProductRepository;
import com.ecommerceAPI.repository.StockRepository;
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
