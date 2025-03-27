package com.getirApp.getirAppBackend.service.stock;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.Stock;
import com.getirApp.getirAppBackend.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    @Override
    public Stock save(Stock stock) {
        return this.stockRepository.save(stock);
    }

    @Override
    public Stock getById(long id) {
        return this.stockRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Stock> getStockList() {
        return stockRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    @Override
    public Stock update(Stock stock) {
        this.getById(stock.getId());
        return this.stockRepository.save(stock);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Stock stock = this.getById(id);
        this.stockRepository.delete(stock);
    }
}
