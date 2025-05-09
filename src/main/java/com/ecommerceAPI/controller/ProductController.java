package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.modelMapper.ModelMapperService;
import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.ProductRequest;
import com.ecommerceAPI.dto.response.ProductResponse;
import com.ecommerceAPI.entity.Product;
import com.ecommerceAPI.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ModelMapperService modelMapper;

    public ProductController(ProductService productService, ModelMapperService modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody ProductRequest productRequest) {
        Product product = this.modelMapper.forRequest().map(productRequest, Product.class);
        this.productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultHelper.created(this.modelMapper.forResponse().map(product, ProductResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Product product = this.productService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(product, ProductResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Product> productList = this.productService.getAll();
        List<ProductResponse> productResponseList = productList.stream().map(product -> this.modelMapper.forResponse().map(product, ProductResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(productResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = this.modelMapper.forRequest().map(productRequest, Product.class);
        product.setId(id);
        this.productService.update(product);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(product, ProductResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.productService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
