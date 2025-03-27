package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.Result;
import com.ecommerceAPI.core.utils.ResultData;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.CategoryRequest;
import com.ecommerceAPI.dto.response.CategoryResponse;
import com.ecommerceAPI.entity.Category;
import com.ecommerceAPI.service.category.CategoryService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResultData<CategoryResponse> save(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = this.modelMapper.forRequest().map(categoryRequest, Category.class);
        this.categoryService.save(category);
        return ResultHelper.created(this.modelMapper.forResponse().map(category, CategoryResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResultData<CategoryResponse> get(@PathVariable("id") int id) {
        Category category = this.categoryService.getById(id);
        CategoryResponse categoryResponse = modelMapper.mapCategoryWithProducts(category);
        return ResultHelper.success(categoryResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CategoryResponse>> getAll() {
        List<Category> categoryList = this.categoryService.getCategoryList();

        List<CategoryResponse> categoryResponseList = categoryList.stream()
                .map(modelMapper::mapCategoryWithProducts)
                .collect(Collectors.toList());

        return ResultHelper.success(categoryResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CategoryResponse> update(@PathVariable int id, @Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = this.modelMapper.forRequest().map(categoryRequest, Category.class);
        category.setId(id);
        this.categoryService.update(category);
        return ResultHelper.success(this.modelMapper.forResponse().map(category, CategoryResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.categoryService.delete(id);
        return ResultHelper.ok();
    }
}
