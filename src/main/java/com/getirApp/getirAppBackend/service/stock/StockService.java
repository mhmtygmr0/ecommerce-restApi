package com.getirApp.getirAppBackend.service.stock;

import com.getirApp.getirAppBackend.entity.Stock;

import java.util.List;

public interface StockService {
    Stock save(Stock stock);

    Stock get(long id);

    List<Stock> getStockList();

    Stock update(Stock stock);

    void delete(long id);
}
