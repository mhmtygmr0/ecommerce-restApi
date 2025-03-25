package com.getirApp.getirAppBackend.repository;

import com.getirApp.getirAppBackend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByOrderByIdAsc();
}
