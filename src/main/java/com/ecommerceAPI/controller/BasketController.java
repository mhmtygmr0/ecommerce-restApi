package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.BasketRequest;
import com.ecommerceAPI.dto.response.BasketResponse;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.service.basket.BasketService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;
    private final ModelMapperService modelMapper;

    public BasketController(BasketService basketService, ModelMapperService modelMapper) {
        this.basketService = basketService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody BasketRequest basketRequest) {
        Basket basket = this.modelMapper.forRequest().map(basketRequest, Basket.class);
        this.basketService.save(basket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultHelper.created(this.modelMapper.forResponse().map(basket, BasketResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Basket basket = this.basketService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(basket, BasketResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Basket> basketList = this.basketService.getAll();
        List<BasketResponse> basketResponseList = basketList.stream()
                .map(basket -> this.modelMapper.forResponse().map(basket, BasketResponse.class))
                .toList();
        return ResponseEntity.ok(ResultHelper.success(basketResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody BasketRequest basketRequest) {
        Basket basket = this.modelMapper.forRequest().map(basketRequest, Basket.class);
        basket.setId(id);
        this.basketService.update(basket);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(basket, BasketResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.basketService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
