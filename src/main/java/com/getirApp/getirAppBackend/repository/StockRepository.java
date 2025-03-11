package com.getirApp.getirAppBackend.repository;

import com.getirApp.getirAppBackend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
