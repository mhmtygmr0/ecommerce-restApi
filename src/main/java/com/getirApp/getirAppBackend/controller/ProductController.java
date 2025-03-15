package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.ProductRequest;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public ResultData<ProductResponse> save(@Valid @RequestBody ProductRequest productRequest) {
        Product product = this.modelMapper.forRequest().map(productRequest, Product.class);
        product.setId(0);

        Category category = this.categoryService.get(productRequest.getCategoryId());
        product.setCategory(category);

        Stock stock = new Stock();
        stock.setQuantity(productRequest.getStockQuantity());

        stockService.save(stock);

        product.setStock(stock);

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
        List<ProductResponse> productResponseList = productList.stream().map(product -> modelMapper.forResponse().map(product, ProductResponse.class)).collect(Collectors.toList());
        return ResultHelper.success(productResponseList);
    }

    @PutMapping("/{id}")
    public ResultData<ProductResponse> update(@PathVariable long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = this.productService.get(id);

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        Category category = this.categoryService.get(productRequest.getCategoryId());
        product.setCategory(category);

        Stock stock = product.getStock();
        if (stock == null) {
            stock = new Stock();
        }
        stock.setQuantity(productRequest.getStockQuantity());
        this.stockService.save(stock);

        product.setStock(stock);

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
