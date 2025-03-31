package com.ecommerceAPI.service.orderItem;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.entity.OrderItem;
import com.ecommerceAPI.entity.Product;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.repository.OrderItemRepository;
import com.ecommerceAPI.service.order.OrderService;
import com.ecommerceAPI.service.product.ProductService;
import com.ecommerceAPI.service.stock.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final StockService stockService;
    private final ProductService productService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderService orderService, StockService stockService, ProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.stockService = stockService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public OrderItem save(OrderItem orderItem) {
        Order order = this.orderService.getById(orderItem.getOrder().getId());

        Product product = this.productService.getById(orderItem.getProduct().getId());

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.INSUFFICIENT_STOCK);
        }

        Stock stock = this.stockService.getById(product.getStock().getId());

        if (stock.getQuantity() < orderItem.getQuantity()) {
            throw new IllegalArgumentException(Msg.INSUFFICIENT_STOCK);
        }

        stock.setQuantity(stock.getQuantity() - orderItem.getQuantity());
        this.stockService.save(stock);

        double newTotalPrice = order.getTotalPrice() + orderItem.getPrice();
        order.setTotalPrice(newTotalPrice);

        this.orderService.save(order, order.getUser().getId(), order.getAddress().getId());

        orderItem.setOrder(order);

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
        OrderItem existingOrderItem = this.getById(orderItem.getId());

        Order order = this.orderService.getById(orderItem.getOrder().getId());

        Product product = this.productService.getById(orderItem.getProduct().getId());

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.NOT_FOUND, "Stock");
        }

        Stock stock = this.stockService.getById(product.getStock().getId());

        order.setTotalPrice(order.getTotalPrice() - existingOrderItem.getPrice());

        Long quantityDiff = orderItem.getQuantity() - existingOrderItem.getQuantity();
        if (quantityDiff > 0) {

            if (stock.getQuantity() < quantityDiff) {
                throw new IllegalArgumentException(Msg.INSUFFICIENT_STOCK);
            }
            stock.setQuantity(stock.getQuantity() - quantityDiff);
        } else if (quantityDiff < 0) {

            stock.setQuantity(stock.getQuantity() + Math.abs(quantityDiff));
        }
        this.stockService.save(stock);

        order.setTotalPrice(order.getTotalPrice() + orderItem.getPrice());

        this.orderService.save(order, order.getUser().getId(), order.getAddress().getId());

        return this.orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        OrderItem orderItem = this.getById(id);

        Order order = this.orderService.getById(orderItem.getOrder().getId());

        Product product = this.productService.getById(orderItem.getProduct().getId());

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.NOT_FOUND, "Stock");
        }

        Stock stock = this.stockService.getById(product.getStock().getId());

        stock.setQuantity(stock.getQuantity() + orderItem.getQuantity());
        this.stockService.save(stock);

        order.setTotalPrice(order.getTotalPrice() - orderItem.getPrice());

        this.orderService.save(order, order.getUser().getId(), order.getAddress().getId());

        this.orderItemRepository.delete(orderItem);
    }

}
