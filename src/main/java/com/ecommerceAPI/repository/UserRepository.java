package com.ecommerceAPI.repository;

import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.CourierStatus;
import com.ecommerceAPI.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByIdAsc();

    List<User> findByRoleAndCourierStatusOrderByAvailableSinceAsc(UserRole role, CourierStatus courierStatus);

    List<User> findByRoleOrderByIdAsc(UserRole role);
    
    Optional<User> findByEmail(String email);
}
