package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.StockRequest;
import com.ecommerceAPI.dto.response.StockResponse;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import com.ecommerceAPI.service.stock.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody StockRequest stockRequest) {
        Stock stock = this.modelMapper.forRequest().map(stockRequest, Stock.class);
        this.stockService.save(stock);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultHelper.created(this.modelMapper.forResponse().map(stock, StockResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Stock stock = this.stockService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(stock, StockResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Stock> stockList = this.stockService.getStockList();
        List<StockResponse> stockResponseList = stockList.stream().map(stock -> this.modelMapper.forResponse().map(stock, StockResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(stockResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody StockRequest stockRequest) {
        Stock stock = this.modelMapper.forRequest().map(stockRequest, Stock.class);
        stock.setId(id);
        this.stockService.update(stock);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(stock, StockResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.stockService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
