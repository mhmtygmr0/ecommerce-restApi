package com.getirApp.getirAppBackend.service.user;

import com.getirApp.getirAppBackend.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User getById(Long id);

    List<User> getUserList();

    User update(User user);

    void delete(Long id);
}
