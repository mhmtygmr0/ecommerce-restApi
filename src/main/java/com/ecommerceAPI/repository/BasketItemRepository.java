package com.ecommerceAPI.repository;

import com.ecommerceAPI.entity.BasketItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    List<BasketItem> findAllByOrderByIdAsc();

    @Transactional
    @Modifying
    @Query("DELETE FROM BasketItem b WHERE b.basket.id = :basketId")
    void deleteByBasketId(Long basketId);
}
