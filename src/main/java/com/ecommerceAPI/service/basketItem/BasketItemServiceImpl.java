package com.ecommerceAPI.service.basketItem;

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

        if (product != null) {
            double price = product.getPrice() * basketItem.getQuantity();
            basketItem.setPrice(price);
        }

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.INSUFFICIENT_STOCK);
        }

        Stock stock = this.stockService.getById(product.getStock().getId());

        if (stock.getQuantity() < basketItem.getQuantity()) {
            throw new IllegalArgumentException(Msg.INSUFFICIENT_STOCK);
        }

        stock.setQuantity(stock.getQuantity() - basketItem.getQuantity());
        this.stockService.save(stock);

        double newTotalPrice = basket.getTotalPrice() + basketItem.getPrice();
        basket.setTotalPrice(newTotalPrice);

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
        BasketItem existingBasketItem = this.getById(basketItem.getId());

        Basket basket = this.basketService.getById(basketItem.getBasket().getId());

        Product product = this.productService.getById(basketItem.getProduct().getId());

        if (product != null) {
            double price = product.getPrice() * basketItem.getQuantity();
            basketItem.setPrice(price);
        }

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.NOT_FOUND, "Stock");
        }

        Stock stock = this.stockService.getById(product.getStock().getId());

        basket.setTotalPrice(basket.getTotalPrice() - existingBasketItem.getPrice());

        Long quantityDiff = basketItem.getQuantity() - existingBasketItem.getQuantity();
        if (quantityDiff > 0) {

            if (stock.getQuantity() < quantityDiff) {
                throw new IllegalArgumentException(Msg.INSUFFICIENT_STOCK);
            }
            stock.setQuantity(stock.getQuantity() - quantityDiff);
        } else if (quantityDiff < 0) {

            stock.setQuantity(stock.getQuantity() + Math.abs(quantityDiff));
        }
        this.stockService.save(stock);

        basket.setTotalPrice(basket.getTotalPrice() + basketItem.getPrice());

        this.basketService.update(basket);

        return this.basketItemRepository.save(basketItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BasketItem basketItem = this.getById(id);

        Basket basket = this.basketService.getById(basketItem.getBasket().getId());

        Product product = this.productService.getById(basketItem.getProduct().getId());

        if (product.getStock() == null) {
            throw new NotFoundException(Msg.NOT_FOUND, "Stock");
        }

        Stock stock = this.stockService.getById(product.getStock().getId());

        stock.setQuantity(stock.getQuantity() + basketItem.getQuantity());
        this.stockService.save(stock);

        basket.setTotalPrice(basket.getTotalPrice() - basketItem.getPrice());

        this.basketService.update(basket);

        this.basketItemRepository.delete(basketItem);
    }

}
