package com.ecommerceAPI.service.modelMapper;

import com.ecommerceAPI.dto.response.CategoryResponse;
import com.ecommerceAPI.entity.Category;

public interface ModelMapperService {

    org.modelmapper.ModelMapper forRequest();

    org.modelmapper.ModelMapper forResponse();

    CategoryResponse mapCategoryWithProducts(Category category);
}
