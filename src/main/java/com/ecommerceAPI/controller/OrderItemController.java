package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.Result;
import com.ecommerceAPI.core.utils.ResultData;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.OrderItemRequest;
import com.ecommerceAPI.dto.response.OrderItemResponse;
import com.ecommerceAPI.entity.OrderItem;
import com.ecommerceAPI.entity.Product;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import com.ecommerceAPI.service.orderItem.OrderItemService;
import com.ecommerceAPI.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/item")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final ModelMapperService modelMapper;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService, ModelMapperService modelMapper, ProductService productService) {
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<OrderItemResponse> save(@Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderItem orderItem = this.modelMapper.forRequest().map(orderItemRequest, OrderItem.class);
        this.orderItemService.save(orderItem);
        return ResultHelper.created(this.modelMapper.forResponse().map(orderItem, OrderItemResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<OrderItemResponse> get(@PathVariable("id") Long id) {
        OrderItem orderItem = this.orderItemService.getById(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(orderItem, OrderItemResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<OrderItemResponse>> getAll() {
        List<OrderItem> orderItemList = this.orderItemService.getAll();
        List<OrderItemResponse> orderItemResponseList = orderItemList.stream()
                .map(orderItem -> this.modelMapper.forResponse().map(orderItem, OrderItemResponse.class))
                .toList();
        return ResultHelper.success(orderItemResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<OrderItemResponse> update(@PathVariable("id") Long id, @Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderItem orderItem = this.modelMapper.forRequest().map(orderItemRequest, OrderItem.class);
        orderItem.setId(id);
        this.orderItemService.update(orderItem);
        return ResultHelper.success(this.modelMapper.forResponse().map(orderItem, OrderItemResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.orderItemService.delete(id);
        return ResultHelper.ok();
    }
}
