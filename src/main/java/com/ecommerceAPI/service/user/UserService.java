package com.ecommerceAPI.service.user;

import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.CourierStatus;

import java.util.List;

public interface UserService {
    User save(User user);

    User getById(Long id);

    List<User> getAll();

    User update(User user);

    void delete(Long id);

    List<User> findAvailableCouriers();

    void updateCourierStatus(Long userId, CourierStatus status);
}
