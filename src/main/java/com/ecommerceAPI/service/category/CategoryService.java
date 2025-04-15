package com.ecommerceAPI.service.category;

import com.ecommerceAPI.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Category save(Category category, MultipartFile imageFile);

    Category getById(Long id);

    List<Category> getCategoryList();

    Category update(Category category, MultipartFile imageFile);

    void delete(Long id);
}
