package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.Result;
import com.ecommerceAPI.core.utils.ResultData;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.BasketRequest;
import com.ecommerceAPI.dto.response.BasketResponse;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.service.basket.BasketService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BasketResponse> save(@Valid @RequestBody BasketRequest basketRequest) {
        Basket basket = this.modelMapper.forRequest().map(basketRequest, Basket.class);
        this.basketService.save(basket);
        return ResultHelper.created(this.modelMapper.forResponse().map(basket, BasketResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BasketResponse> get(@PathVariable("id") Long id) {
        Basket basket = this.basketService.getById(id);
        return ResultHelper.success(modelMapper.forResponse().map(basket, BasketResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<BasketResponse>> getAll() {
        List<Basket> basketList = this.basketService.getAll();
        List<BasketResponse> basketResponseList = basketList.stream().map(basket -> this.modelMapper.forResponse().map(basket, BasketResponse.class)).toList();
        return ResultHelper.success(basketResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BasketResponse> update(@PathVariable("id") Long id, @Valid @RequestBody BasketRequest basketRequest) {
        Basket basket = this.modelMapper.forRequest().map(basketRequest, Basket.class);
        basket.setId(id);
        this.basketService.update(basket);
        return ResultHelper.success(this.modelMapper.forResponse().map(basket, BasketResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.basketService.delete(id);
        return ResultHelper.ok();
    }
}
