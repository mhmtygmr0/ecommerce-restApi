package com.getirApp.getirAppBackend.service.product;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.Product;
import com.getirApp.getirAppBackend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Product update(Product product) {
        this.get(product.getId());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Product product = this.get(id);
        this.productRepository.delete(product);
    }
}