package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.product.ProductSaveRequest;
import com.getirApp.getirAppBackend.dto.request.product.ProductUpdateRequest;
import com.getirApp.getirAppBackend.dto.response.ProductResponse;
import com.getirApp.getirAppBackend.entity.Category;
import com.getirApp.getirAppBackend.entity.Product;
import com.getirApp.getirAppBackend.service.category.CategoryService;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import com.getirApp.getirAppBackend.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ModelMapperService modelMapper;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, ModelMapperService modelMapper, CategoryService categoryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<ProductResponse> save(@Valid @RequestBody ProductSaveRequest productSaveRequest) {
        Product product = this.modelMapper.forRequest().map(productSaveRequest, Product.class);

        product.setId(0);

        Category category = this.categoryService.get(productSaveRequest.getCategoryId());
        product.setCategory(category);

        this.productService.save(product);

        return ResultHelper.created(this.modelMapper.forResponse().map(product, ProductResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ProductResponse> get(@PathVariable("id") long id) {
        Product product = this.productService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(product, ProductResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<ProductResponse>> getAll() {
        List<Product> productList = this.productService.getAll();
        List<ProductResponse> productResponseList = productList.stream()
                .map(product -> modelMapper.forResponse().map(product, ProductResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(productResponseList);
    }

    @PutMapping("/{id}")
    public ResultData<ProductResponse> update(@PathVariable long id, @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        Product product = this.modelMapper.forRequest().map(productUpdateRequest, Product.class);
        product.setId(id);
        this.productService.update(product);
        return ResultHelper.success(this.modelMapper.forResponse().map(product, ProductResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.productService.delete(id);
        return ResultHelper.ok();
    }
}
