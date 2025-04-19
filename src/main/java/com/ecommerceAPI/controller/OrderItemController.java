package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.response.OrderItemResponse;
import com.ecommerceAPI.entity.OrderItem;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import com.ecommerceAPI.service.orderItem.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order/item")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final ModelMapperService modelMapper;

    public OrderItemController(OrderItemService orderItemService, ModelMapperService modelMapper) {
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        OrderItem orderItem = this.orderItemService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(orderItem, OrderItemResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<OrderItem> orderItemList = this.orderItemService.getAll();
        List<OrderItemResponse> orderItemResponseList = orderItemList.stream().map(orderItem -> this.modelMapper.forResponse().map(orderItem, OrderItemResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(orderItemResponseList));
    }
}
