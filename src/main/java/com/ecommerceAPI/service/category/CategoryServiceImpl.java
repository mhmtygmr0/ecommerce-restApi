package com.ecommerceAPI.service.category;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Category;
import com.ecommerceAPI.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category getById(int id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Category> getCategoryList() {
        return this.categoryRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Category update(Category category) {
        this.getById(category.getId());
        return this.categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Category category = this.getById(id);
        this.categoryRepository.delete(category);
    }
}
