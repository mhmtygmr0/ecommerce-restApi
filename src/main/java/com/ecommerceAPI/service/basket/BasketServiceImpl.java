package com.ecommerceAPI.service.basket;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.repository.BasketRepository;
import com.ecommerceAPI.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;

    public BasketServiceImpl(BasketRepository basketRepository, UserService userService) {
        this.basketRepository = basketRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Basket save(Basket basket) {
        basket.setId(null);
        basket.setUser(this.userService.getById(basket.getUser().getId()));
        basket.setTotalPrice(basket.getTotalPrice() != null ? basket.getTotalPrice() : 0.0);
        return this.basketRepository.save(basket);
    }

    @Override
    public Basket getById(Long id) {
        return this.basketRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Basket"));
    }

    @Override
    public List<Basket> getAll() {
        return this.basketRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Basket update(Basket basket) {
        Basket existingBasket = this.getById(basket.getId());
        basket.setUser(this.userService.getById(basket.getUser().getId()));
        basket.setTotalPrice(basket.getTotalPrice() != null ? basket.getTotalPrice() : existingBasket.getTotalPrice());
        return this.basketRepository.save(basket);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Basket basket = getById(id);
        this.basketRepository.delete(basket);
    }
}
