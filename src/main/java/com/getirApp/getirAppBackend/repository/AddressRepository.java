package com.getirApp.getirAppBackend.repository;

import com.getirApp.getirAppBackend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByOrderByIdAsc();

    @Query("SELECT COUNT(a) > 0 FROM Address a WHERE a.id = :addressId AND a.user.id = :userId")
    boolean existsByIdAndUserId(@Param("addressId") Long addressId, @Param("userId") Long userId);
}
