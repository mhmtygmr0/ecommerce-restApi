package com.ecommerceAPI.service.stock;

import com.ecommerceAPI.entity.Stock;

import java.util.List;

public interface StockService {
    Stock save(Stock stock);

    Stock getById(Long id);

    List<Stock> getAll();

    Stock update(Stock stock);

    void delete(Long id);
}
