package com.ecommerceAPI.service.product;

import com.ecommerceAPI.entity.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product getById(long id);

    List<Product> getAll();

    Product update(Product product);

    void delete(long id);
}
