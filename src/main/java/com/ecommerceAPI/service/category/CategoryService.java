package com.ecommerceAPI.service.category;

import com.ecommerceAPI.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category getById(Long id);

    List<Category> getCategoryList();

    Category update(Category category);

    void delete(Long id);
}
