package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.stock.StockSaveRequest;
import com.getirApp.getirAppBackend.dto.request.stock.StockUpdateRequest;
import com.getirApp.getirAppBackend.dto.response.StockResponse;
import com.getirApp.getirAppBackend.entity.Stock;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import com.getirApp.getirAppBackend.service.stock.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResultData<StockResponse> save(@Valid @RequestBody StockSaveRequest stockSaveRequest) {
        Stock stock = this.modelMapper.forRequest().map(stockSaveRequest, Stock.class);
        this.stockService.save(stock);
        return ResultHelper.created(this.modelMapper.forResponse().map(stock, StockResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<StockResponse> get(@PathVariable("id") int id) {
        Stock stock = this.stockService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(stock, StockResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<StockResponse>> getAll() {
        List<Stock> stockList = this.stockService.getStockList();

        List<StockResponse> stockResponses = stockList.stream().map(stock -> new StockResponse(stock.getId(), stock.getQuantity(), stock.getUpdatedAt())).toList();

        return ResultHelper.success(stockResponses);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<StockResponse> update(@Valid @RequestBody StockUpdateRequest stockUpdateRequest) {
        Stock stock = this.modelMapper.forRequest().map(stockUpdateRequest, Stock.class);
        this.stockService.update(stock);
        return ResultHelper.success(this.modelMapper.forResponse().map(stock, StockResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.stockService.delete(id);
        return ResultHelper.ok();
    }
}
