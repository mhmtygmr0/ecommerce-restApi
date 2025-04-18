package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.OrderRequest;
import com.ecommerceAPI.dto.response.OrderResponse;
import com.ecommerceAPI.entity.Order;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import com.ecommerceAPI.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = this.modelMapper.forRequest().map(orderRequest, Order.class);
        this.orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultHelper.created(this.modelMapper.forResponse().map(order, OrderResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Order order = this.orderService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(order, OrderResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Order> orderList = this.orderService.getAll();
        List<OrderResponse> orderResponseList = orderList.stream().map(order -> this.modelMapper.forResponse().map(order, OrderResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(orderResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody OrderRequest orderRequest) {
        Order order = this.modelMapper.forRequest().map(orderRequest, Order.class);
        order.setId(id);
        this.orderService.update(order);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(order, OrderResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.orderService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
