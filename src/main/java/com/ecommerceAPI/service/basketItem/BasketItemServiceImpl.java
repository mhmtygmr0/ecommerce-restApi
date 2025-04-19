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

    @Override
    @Transactional
    public BasketItem save(BasketItem basketItem) {
        Basket basket = this.basketService.getById(basketItem.getBasket().getId());
        Product product = this.productService.getById(basketItem.getProduct().getId());

        boolean productExists = basket.getBasketItemList().stream().anyMatch(item -> item.getProduct().getId().equals(product.getId()));

        if (productExists) {
            throw new BusinessException(Msg.PRODUCT_ALREADY_IN_BASKET);
        }

        basketItem.setTotalPrice(product.getPrice() * basketItem.getQuantity());

        Stock stock = this.stockService.getById(product.getStock().getId());
        if (stock.getQuantity() < basketItem.getQuantity()) {
            throw new BusinessException(Msg.INSUFFICIENT_STOCK);
        }

        basket.setTotalPrice(basket.getTotalPrice() + basketItem.getTotalPrice());
        this.basketService.update(basket);
        basketItem.setBasket(basket);

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
    @Transactional
    public BasketItem update(BasketItem basketItem) {
        BasketItem oldBasketItem = this.getById(basketItem.getId());
        Basket basket = this.basketService.getById(basketItem.getBasket().getId());
        Product product = this.productService.getById(basketItem.getProduct().getId());

        boolean productExists = basket.getBasketItemList().stream().filter(item -> !item.getId().equals(oldBasketItem.getId())).anyMatch(item -> item.getProduct().getId().equals(product.getId()));

        if (productExists) {
            throw new BusinessException(Msg.PRODUCT_ALREADY_IN_BASKET);
        }

        basketItem.setTotalPrice(product.getPrice() * basketItem.getQuantity());

        Stock stock = this.stockService.getById(product.getStock().getId());
        if (stock.getQuantity() < basketItem.getQuantity()) {
            throw new BusinessException(Msg.INSUFFICIENT_STOCK);
        }

        basket.setTotalPrice(basket.getTotalPrice() - oldBasketItem.getTotalPrice());
        basket.setTotalPrice(basket.getTotalPrice() + basketItem.getTotalPrice());
        this.basketService.update(basket);
        basketItem.setBasket(basket);

        return this.basketItemRepository.save(basketItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BasketItem basketItem = this.getById(id);
        Basket basket = this.basketService.getById(basketItem.getBasket().getId());
        basket.setTotalPrice(basket.getTotalPrice() - basketItem.getTotalPrice());
        this.basketService.update(basket);
        this.basketItemRepository.delete(basketItem);
    }

    @Override
    @Transactional
    public void deleteByBasketId(Long basketId) {
        Basket basket = this.basketService.getById(basketId);
        List<BasketItem> items = basket.getBasketItemList();

        double totalToRemove = items.stream().mapToDouble(BasketItem::getTotalPrice).sum();

        basket.setTotalPrice(basket.getTotalPrice() - totalToRemove);
        this.basketService.update(basket);

        this.basketItemRepository.deleteAll(items);
    }

}
