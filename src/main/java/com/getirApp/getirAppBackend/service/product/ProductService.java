package com.getirApp.getirAppBackend.service.product;

import com.getirApp.getirAppBackend.entity.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product get(long id);

    List<Product> getAll();

    Product update(Product product);

    void delete(long id);
}
