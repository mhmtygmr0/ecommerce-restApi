package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.modelMapper.ModelMapperService;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.BasketItemRequest;
import com.ecommerceAPI.dto.response.BasketItemResponse;
import com.ecommerceAPI.entity.BasketItem;
import com.ecommerceAPI.service.basketItem.BasketItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/basket/item")
public class BasketItemController {
    private final BasketItemService basketItemService;
    private final ModelMapperService modelMapper;

    public BasketItemController(BasketItemService basketItemService, ModelMapperService modelMapper) {
        this.basketItemService = basketItemService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody BasketItemRequest basketItemRequest) {
        BasketItem basketItem = this.modelMapper.forRequest().map(basketItemRequest, BasketItem.class);
        this.basketItemService.save(basketItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultHelper.created(this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        BasketItem basketItem = this.basketItemService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<BasketItem> basketItemList = this.basketItemService.getAll();
        List<BasketItemResponse> basketItemResponseList = basketItemList.stream().map(basketItem -> this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(basketItemResponseList));
    }

    @GetMapping("/basket/{basketId}")
    public ResponseEntity<Map<String, Object>> getBasketItemsByBasketId(@PathVariable("basketId") Long basketId) {
        List<BasketItem> basketItemList = this.basketItemService.getBasketItemsByBasketId(basketId);
        List<BasketItemResponse> basketItemResponseList = basketItemList.stream().map(basketItem -> this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(basketItemResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody BasketItemRequest basketItemRequest) {
        BasketItem basketItem = this.modelMapper.forRequest().map(basketItemRequest, BasketItem.class);
        basketItem.setId(id);
        this.basketItemService.update(basketItem);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.basketItemService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
