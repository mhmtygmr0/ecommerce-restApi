package com.ecommerceAPI.service.user;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.CourierStatus;
import com.ecommerceAPI.enums.UserRole;
import com.ecommerceAPI.repository.UserRepository;
import com.ecommerceAPI.service.basket.BasketService;
import com.ecommerceAPI.service.delivery.DeliveryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BasketService basketService;
    private final DeliveryService deliveryService;

    public UserServiceImpl(UserRepository userRepository, BasketService basketService, @Lazy DeliveryService deliveryService) {
        this.userRepository = userRepository;
        this.basketService = basketService;
        this.deliveryService = deliveryService;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.getRole() == UserRole.CUSTOMER) {
            Basket basket = new Basket();
            basket.setUser(user);
            this.basketService.save(basket);
            user.setBasket(basket);
        }
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
    public List<User> getAllByRole(UserRole role) {
        return this.userRepository.findByRoleOrderByIdAsc(role);
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

    @Override
    public List<User> findAvailableCouriers() {
        return this.userRepository.findByRoleAndCourierStatusOrderByAvailableSinceAsc(
                UserRole.COURIER,
                CourierStatus.AVAILABLE
        );
    }

    @Override
    @Transactional
    public void updateCourierStatus(Long userId, CourierStatus status) {
        User courier = this.getById(userId);

        if (courier.getRole() != UserRole.COURIER) {
            throw new NotFoundException(Msg.NOT_FOUND, "Courier");
        }

        courier.setCourierStatus(status);

        if (status == CourierStatus.AVAILABLE) {
            courier.setAvailableSince(LocalDateTime.now());
        } else {
            courier.setAvailableSince(null);
        }

        this.userRepository.save(courier);

        if (status == CourierStatus.AVAILABLE) {
            this.deliveryService.assignAvailableCourierToPendingDelivery();
        }
    }
}