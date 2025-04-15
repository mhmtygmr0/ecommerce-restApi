package com.ecommerceAPI.service.category;

import com.ecommerceAPI.core.exception.ImageSaveException;
import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Category;
import com.ecommerceAPI.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category save(Category category, MultipartFile imageFile) {
        String imageUrl = saveImage(imageFile);
        category.setImageUrl(imageUrl);

        return this.categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Category"));
    }

    @Override
    public List<Category> getCategoryList() {
        return this.categoryRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Category update(Category category, MultipartFile imageFile) {
        Category existingCategory = this.getById(category.getId());

        if (imageFile != null) {
            String imageUrl = saveImage(imageFile);
            category.setImageUrl(imageUrl);
        } else {
            category.setImageUrl(existingCategory.getImageUrl());
        }

        category.setProductList(existingCategory.getProductList());
        return this.categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = this.getById(id);
        this.categoryRepository.delete(category);
    }

    private String saveImage(MultipartFile imageFile) {
        String uploadDir = "images/categoryImages/";
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        try {
            Files.write(filePath, imageFile.getBytes());
        } catch (IOException e) {
            throw new ImageSaveException(Msg.IMAGE_SAVE_FAILED, e);
        }

        return "/images/categoryImages/" + fileName;
    }
}
