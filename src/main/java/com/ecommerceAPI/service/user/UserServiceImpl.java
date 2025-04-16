package com.ecommerceAPI.service.user;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.UserRole;
import com.ecommerceAPI.repository.UserRepository;
import com.ecommerceAPI.service.basket.BasketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BasketService basketService;

    public UserServiceImpl(UserRepository userRepository, BasketService basketService) {
        this.userRepository = userRepository;
        this.basketService = basketService;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }
        Basket basket = new Basket();
        basket.setUser(user);
        this.basketService.save(basket);
        user.setBasket(basket);
        return this.userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "User"));
    }

    @Override
    public List<User> getAll() {
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
