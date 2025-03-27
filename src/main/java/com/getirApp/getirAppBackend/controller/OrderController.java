package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.OrderRequest;
import com.getirApp.getirAppBackend.dto.response.OrderResponse;
import com.getirApp.getirAppBackend.entity.Order;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import com.getirApp.getirAppBackend.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapperService modelMapper;

    public OrderController(OrderService orderService, ModelMapperService modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<OrderResponse> save(@Valid @RequestBody OrderRequest orderRequest) {
        Long userId = orderRequest.getUserId();
        Long addressId = orderRequest.getAddressId();

        Order order = this.modelMapper.forRequest().map(orderRequest, Order.class);

        Order savedOrder = this.orderService.save(order, userId, addressId);

        return ResultHelper.created(this.modelMapper.forResponse().map(savedOrder, OrderResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<OrderResponse> get(@PathVariable("id") Long id) {
        Order order = this.orderService.getById(id);
        return ResultHelper.success(modelMapper.forResponse().map(order, OrderResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<OrderResponse>> getAll() {
        List<Order> orderList = this.orderService.getAll();
        List<OrderResponse> orderResponseList = orderList.stream().map(order -> this.modelMapper.forResponse().map(order, OrderResponse.class)).toList();
        return ResultHelper.success(orderResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<OrderResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest orderRequest) {
        Long userId = orderRequest.getUserId();
        Long addressId = orderRequest.getAddressId();

        Order order = this.modelMapper.forRequest().map(orderRequest, Order.class);
        order.setId(id);

        Order updatedOrder = this.orderService.update(order, userId, addressId);

        return ResultHelper.success(this.modelMapper.forResponse().map(updatedOrder, OrderResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.orderService.delete(id);
        return ResultHelper.ok();
    }
}
