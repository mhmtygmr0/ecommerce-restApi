package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.CategoryRequest;
import com.ecommerceAPI.dto.response.CategoryResponse;
import com.ecommerceAPI.entity.Category;
import com.ecommerceAPI.service.category.CategoryService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapperService modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapperService modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = this.modelMapper.forRequest().map(categoryRequest, Category.class);
        this.categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultHelper.created(this.modelMapper.forResponse().map(category, CategoryResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Category category = this.categoryService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(category, CategoryResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Category> categoryList = this.categoryService.getCategoryList();
        List<CategoryResponse> categoryResponseList = categoryList.stream().map(category -> this.modelMapper.forResponse().map(category, CategoryResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(categoryResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = this.modelMapper.forRequest().map(categoryRequest, Category.class);
        category.setId(id);
        this.categoryService.update(category);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(category, CategoryResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
