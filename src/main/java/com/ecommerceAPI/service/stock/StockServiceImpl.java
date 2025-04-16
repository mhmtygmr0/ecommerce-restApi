package com.ecommerceAPI.service.stock;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public Stock save(Stock stock) {
        return this.stockRepository.save(stock);
    }

    @Override
    public Stock getById(Long id) {
        return this.stockRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Stock"));
    }

    @Override
    public List<Stock> getAll() {
        return this.stockRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Stock update(Stock stock) {
        this.getById(stock.getId());
        return this.stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Stock stock = this.getById(id);
        this.stockRepository.delete(stock);
    }
}
