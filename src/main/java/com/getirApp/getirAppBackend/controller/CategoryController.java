package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.ModelMapperService;
import com.getirApp.getirAppBackend.service.CategoryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    CategoryService categoryService;
    ModelMapperService modelMapper;
}
