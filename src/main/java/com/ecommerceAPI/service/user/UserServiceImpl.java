package com.ecommerceAPI.service.user;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.UserRole;
import com.ecommerceAPI.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }
        return this.userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "User"));
    }

    @Override
    public List<User> getUserList() {
        return this.userRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public User update(User user) {
        User existingUser = this.getById(user.getId());

        if (user.getRole() == null) {
            user.setRole(existingUser.getRole());
        }
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(existingUser.getCreatedAt());
        }

        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = this.getById(id);
        this.userRepository.delete(user);
    }
}
