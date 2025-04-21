package com.ecommerceAPI.service.product;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Category;
import com.ecommerceAPI.entity.Product;
import com.ecommerceAPI.entity.Stock;
import com.ecommerceAPI.repository.ProductRepository;
import com.ecommerceAPI.service.category.CategoryService;
import com.ecommerceAPI.service.stock.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final StockService stockService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, StockService stockService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.stockService = stockService;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        product.setId(null);

        Category category = this.categoryService.getById(product.getCategory().getId());
        product.setCategory(category);

        Stock stock = this.handleStock(null, product.getStock().getQuantity());
        product.setStock(stock);

        return this.productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Product"));
    }

    @Override
    public List<Product> getAll() {
        return this.productRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Product update(Product product) {
        Product existingProduct = this.getById(product.getId());

        Category category = this.categoryService.getById(product.getCategory().getId());
        product.setCategory(category);

        if (product.getStock() != null && product.getStock().getQuantity() != null) {
            Stock stock = this.handleStock(existingProduct.getStock(), product.getStock().getQuantity());
            product.setStock(stock);
        }

        return this.productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = this.getById(id);
        this.productRepository.delete(product);
    }

    private Stock handleStock(Stock existingStock, Long quantity) {
        if (existingStock != null) {
            existingStock.setQuantity(quantity);
            this.stockService.update(existingStock);
            return existingStock;
        } else {
            Stock newStock = new Stock();
            newStock.setQuantity(quantity);
            this.stockService.save(newStock);
            return newStock;
        }
    }
}