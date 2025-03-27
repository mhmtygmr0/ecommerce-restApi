package com.getirApp.getirAppBackend.service.category;

import com.getirApp.getirAppBackend.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category getById(int id);

    List<Category> getCategoryList();

    Category update(Category category);

    void delete(int id);
}
