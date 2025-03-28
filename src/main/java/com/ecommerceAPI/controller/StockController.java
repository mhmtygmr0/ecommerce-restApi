package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.Result;
import com.ecommerceAPI.core.utils.ResultData;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.StockRequest;
import com.ecommerceAPI.dto.response.StockResponse;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import com.ecommerceAPI.service.stock.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final ModelMapperService modelMapper;

    public StockController(StockService stockService, ModelMapperService modelMapper) {
        this.stockService = stockService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<StockResponse> save(@Valid @RequestBody StockRequest stockRequest) {
        Stock stock = this.modelMapper.forRequest().map(stockRequest, Stock.class);
        this.stockService.save(stock);
        return ResultHelper.created(this.modelMapper.forResponse().map(stock, StockResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<StockResponse> get(@PathVariable("id") Long id) {
        Stock stock = this.stockService.getById(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(stock, StockResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<StockResponse>> getAll() {
        List<Stock> stockList = this.stockService.getStockList();

        List<StockResponse> stockResponseList = stockList.stream()
                .map(stock -> this.modelMapper.forResponse().map(stock, StockResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(stockResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<StockResponse> update(@PathVariable("id") Long id, @Valid @RequestBody StockRequest stockRequest) {
        Stock stock = this.modelMapper.forRequest().map(stockRequest, Stock.class);
        stock.setId(id);
        this.stockService.update(stock);
        return ResultHelper.success(this.modelMapper.forResponse().map(stock, StockResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.stockService.delete(id);
        return ResultHelper.ok();
    }
}
