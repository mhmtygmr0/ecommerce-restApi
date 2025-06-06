package com.ecommerceAPI.repository;

import com.ecommerceAPI.entity.Delivery;
import com.ecommerceAPI.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByOrderByIdAsc();
    
    List<Delivery> findByStatusOrderByAssignedAtAsc(DeliveryStatus status);
}
