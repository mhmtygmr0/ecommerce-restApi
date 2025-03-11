package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.product.ProductSaveRequest;
import com.getirApp.getirAppBackend.dto.response.ProductResponse;
import com.getirApp.getirAppBackend.entity.Category;
import com.getirApp.getirAppBackend.entity.Product;
import com.getirApp.getirAppBackend.entity.Stock;
import com.getirApp.getirAppBackend.service.category.CategoryService;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import com.getirApp.getirAppBackend.service.product.ProductService;
import com.getirApp.getirAppBackend.service.stock.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ModelMapperService modelMapper;
    private final CategoryService categoryService;
    private final StockService stockService;

    public ProductController(ProductService productService, ModelMapperService modelMapper, CategoryService categoryService, StockService stockService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.stockService = stockService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<ProductResponse> save(@Valid @RequestBody ProductSaveRequest productSaveRequest) {
        Product product = this.modelMapper.forRequest().map(productSaveRequest, Product.class);

        Category category = this.categoryService.get(productSaveRequest.getCategoryId());
        product.setCategory(category);

        Stock stock = new Stock();
        stock.setQuantity(productSaveRequest.getStockQuantity());
        this.stockService.save(stock);
        product.setStock(stock);

        this.productService.save(product);

        return ResultHelper.created(this.modelMapper.forResponse().map(product, ProductResponse.class));
    }
}
