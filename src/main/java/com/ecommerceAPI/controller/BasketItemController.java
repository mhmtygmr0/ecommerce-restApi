package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.Result;
import com.ecommerceAPI.core.utils.ResultData;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.BasketItemRequest;
import com.ecommerceAPI.dto.response.BasketItemResponse;
import com.ecommerceAPI.entity.BasketItem;
import com.ecommerceAPI.service.basketItem.BasketItemService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BasketItemResponse> save(@Valid @RequestBody BasketItemRequest basketItemRequest) {
        BasketItem basketItem = this.modelMapper.forRequest().map(basketItemRequest, BasketItem.class);
        this.basketItemService.save(basketItem);
        return ResultHelper.created(this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BasketItemResponse> get(@PathVariable("id") Long id) {
        BasketItem basketItem = this.basketItemService.getById(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<BasketItemResponse>> getAll() {
        List<BasketItem> basketItemList = this.basketItemService.getAll();
        List<BasketItemResponse> basketItemResponseList = basketItemList.stream()
                .map(basketItem -> this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class))
                .toList();
        return ResultHelper.success(basketItemResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BasketItemResponse> update(@PathVariable("id") Long id, @Valid @RequestBody BasketItemRequest basketItemRequest) {
        BasketItem basketItem = this.modelMapper.forRequest().map(basketItemRequest, BasketItem.class);
        basketItem.setId(id);
        this.basketItemService.update(basketItem);
        return ResultHelper.success(this.modelMapper.forResponse().map(basketItem, BasketItemResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.basketItemService.delete(id);
        return ResultHelper.ok();
    }
}
