package com.getirApp.getirAppBackend.service.modelMapper;

import com.getirApp.getirAppBackend.dto.response.CategoryResponse;
import com.getirApp.getirAppBackend.entity.Category;

public interface ModelMapperService {

    org.modelmapper.ModelMapper forRequest();

    org.modelmapper.ModelMapper forResponse();

    CategoryResponse mapCategoryWithProducts(Category category);
}
