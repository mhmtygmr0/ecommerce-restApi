package com.ecommerceAPI.repository;

import com.ecommerceAPI.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByOrderByIdAsc();
}
