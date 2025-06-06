package com.ecommerceAPI.service.order;

import com.ecommerceAPI.core.exception.BadRequestException;
import com.ecommerceAPI.core.exception.BusinessException;
import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.*;
import com.ecommerceAPI.repository.OrderRepository;
import com.ecommerceAPI.service.address.AddressService;
import com.ecommerceAPI.service.basket.BasketService;
import com.ecommerceAPI.service.basketItem.BasketItemService;
import com.ecommerceAPI.service.delivery.DeliveryService;
import com.ecommerceAPI.service.orderItem.OrderItemService;
import com.ecommerceAPI.service.stock.StockService;
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
    private final BasketItemService basketItemService;
    private final OrderItemService orderItemService;
    private final StockService stockService;
    private final DeliveryService deliveryService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, AddressService addressService, BasketService basketService, BasketItemService basketItemService, OrderItemService orderItemService, StockService stockService, DeliveryService deliveryService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.basketService = basketService;
        this.basketItemService = basketItemService;
        this.orderItemService = orderItemService;
        this.stockService = stockService;
        this.deliveryService = deliveryService;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        order.setId(null);

        User user = this.userService.getById(order.getUser().getId());
        Address address = this.addressService.getById(order.getAddress().getId());
        Basket basket = this.basketService.getById(user.getBasket().getId());

        this.addressService.checkAddressBelongsToUser(address.getId(), user.getId());

        List<BasketItem> basketItems = basket.getBasketItemList();
        if (basketItems == null || basketItems.isEmpty()) {
            throw new BadRequestException(Msg.CART_IS_EMPTY);
        }

        order.setUser(user);
        order.setAddress(address);
        order.setTotalPrice(basket.getTotalPrice());

        Order savedOrder = this.orderRepository.save(order);

        this.processBasketItems(savedOrder, basketItems);

        this.basketItemService.deleteByBasketId(basket.getId());

        this.deliveryService.assignCourierToOrder(savedOrder);

        return savedOrder;
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

    private void processBasketItems(Order order, List<BasketItem> basketItems) {
        for (BasketItem basketItem : basketItems) {
            this.updateStock(basketItem);

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(basketItem.getQuantity());
            orderItem.setTotalPrice(basketItem.getTotalPrice());
            orderItem.setOrder(order);
            orderItem.setProduct(basketItem.getProduct());
            orderItem.setName(basketItem.getProduct().getName());
            orderItem.setPrice(basketItem.getProduct().getPrice());
            orderItem.setImageUrl(basketItem.getProduct().getImageUrl());

            this.orderItemService.save(orderItem);
        }
    }

    private void updateStock(BasketItem item) {
        Stock stock = item.getProduct().getStock();
        long updatedQuantity = stock.getQuantity() - item.getQuantity();
        if (updatedQuantity < 0) {
            throw new BusinessException(Msg.INSUFFICIENT_STOCK);
        }
        stock.setQuantity(updatedQuantity);
        stockService.update(stock);
    }
}
