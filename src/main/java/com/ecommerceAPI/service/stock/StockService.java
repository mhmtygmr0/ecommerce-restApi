package com.ecommerceAPI.service.stock;

import com.ecommerceAPI.entity.Stock;

import java.util.List;

public interface StockService {
    Stock save(Stock stock);

    Stock getById(long id);

    List<Stock> getStockList();

    Stock update(Stock stock);

    void delete(long id);
}
