package com.ecommerceAPI.service.basketItem;

import com.ecommerceAPI.core.exception.BusinessException;
import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Basket;
import com.ecommerceAPI.entity.BasketItem;
import com.ecommerceAPI.entity.Product;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.repository.BasketItemRepository;
import com.ecommerceAPI.service.basket.BasketService;
import com.ecommerceAPI.service.product.ProductService;
import com.ecommerceAPI.service.stock.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketItemRepository basketItemRepository;
    private final BasketService basketService;
    private final StockService stockService;
    private final ProductService productService;

    public BasketItemServiceImpl(BasketItemRepository basketItemRepository, BasketService basketService, StockService stockService, ProductService productService) {
        this.basketItemRepository = basketItemRepository;
        this.basketService = basketService;
        this.stockService = stockService;
        this.productService = productService;
    }

    @Transactional
    public BasketItem save(BasketItem basketItem) {
        Basket basket = this.basketService.getById(basketItem.getBasket().getId());

        boolean productExists = basket.getBasketItemList().stream().anyMatch(item -> item.getProduct().getId().equals(basketItem.getProduct().getId()));

        if (productExists) {
            throw new BusinessException(Msg.PRODUCT_ALREADY_IN_BASKET);
        }

        this.calculateBasketItemPriceAndUpdateBasket(basketItem, basket, false, 0.0);
        return this.basketItemRepository.save(basketItem);
    }

    @Override
    public BasketItem getById(Long id) {
        return this.basketItemRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "BasketItem"));
    }

    @Override
    public List<BasketItem> getAll() {
        return this.basketItemRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<BasketItem> getBasketItemsByBasketId(Long basketId) {
        return this.basketItemRepository.findAllByBasketId(basketId);
    }

    @Transactional
    public BasketItem update(BasketItem basketItem) {
        BasketItem oldBasketItem = this.getById(basketItem.getId());
        Basket basket = this.basketService.getById(basketItem.getBasket().getId());

        boolean productExists = basket.getBasketItemList().stream().filter(item -> !item.getId().equals(oldBasketItem.getId())).anyMatch(item -> item.getProduct().getId().equals(basketItem.getProduct().getId()));

        if (productExists) {
            throw new BusinessException(Msg.PRODUCT_ALREADY_IN_BASKET);
        }

        calculateBasketItemPriceAndUpdateBasket(basketItem, basket, true, oldBasketItem.getTotalPrice());
        return this.basketItemRepository.save(basketItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BasketItem basketItem = this.getById(id);
        Basket basket = basketItem.getBasket();

        Double currentTotal = basket.getTotalPrice() != null ? basket.getTotalPrice() : 0.0;
        Double itemPrice = basketItem.getTotalPrice() != null ? basketItem.getTotalPrice() : 0.0;

        double updatedTotal = currentTotal - itemPrice;
        basket.setTotalPrice(Math.max(updatedTotal, 0.0));

        this.basketService.update(basket);
        this.basketItemRepository.delete(basketItem);
    }

    @Override
    @Transactional
    public void deleteByBasketId(Long basketId) {
        Basket basket = this.basketService.getById(basketId);
        List<BasketItem> items = basket.getBasketItemList();

        if (items == null || items.isEmpty()) {
            return;
        }

        double totalToRemove = items.stream()
                .mapToDouble(item -> item.getTotalPrice() != null ? item.getTotalPrice() : 0.0)
                .sum();

        double currentTotal = basket.getTotalPrice() != null ? basket.getTotalPrice() : 0.0;
        basket.setTotalPrice(Math.max(currentTotal - totalToRemove, 0.0));

        this.basketService.update(basket);

        this.basketItemRepository.deleteByBasketId(basketId);
    }

    private void calculateBasketItemPriceAndUpdateBasket(BasketItem basketItem, Basket basket, boolean isUpdate, Double oldItemTotalPrice) {
        Product product = this.productService.getById(basketItem.getProduct().getId());
        Stock stock = this.stockService.getById(product.getStock().getId());

        if (stock.getQuantity() < basketItem.getQuantity()) {
            throw new BusinessException(Msg.INSUFFICIENT_STOCK);
        }

        double newTotalPrice = product.getPrice() * basketItem.getQuantity();
        basketItem.setTotalPrice(newTotalPrice);

        if (isUpdate) {
            basket.setTotalPrice(basket.getTotalPrice() - oldItemTotalPrice + newTotalPrice);
        } else {
            basket.setTotalPrice(basket.getTotalPrice() + newTotalPrice);
        }

        this.basketService.update(basket);
        basketItem.setBasket(basket);
    }
}
