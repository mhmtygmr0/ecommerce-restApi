package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.CategorySaveRequest;
import com.getirApp.getirAppBackend.dto.response.CategoryResponse;
import com.getirApp.getirAppBackend.entity.Category;
import com.getirApp.getirAppBackend.service.category.CategoryService;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CategoryResponse> save(@Valid @RequestBody CategorySaveRequest categorySaveRequest) {
        Category category = this.modelMapper.forRequest().map(categorySaveRequest, Category.class);
        this.categoryService.save(category);
        return ResultHelper.created(this.modelMapper.forResponse().map(category, CategoryResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CategoryResponse> get(@PathVariable("id") int id) {
        Category category = this.categoryService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(category, CategoryResponse.class));
    }
}
