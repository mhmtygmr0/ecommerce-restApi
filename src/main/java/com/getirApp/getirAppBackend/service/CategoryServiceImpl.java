package com.getirApp.getirAppBackend.service;

import com.getirApp.getirAppBackend.entity.Category;
import com.getirApp.getirAppBackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category get(int id) {
        return this.categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category category) {
        this.get(category.getId());
        return this.categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        Category category = this.get(id);
        this.categoryRepository.delete(category);
    }
}
