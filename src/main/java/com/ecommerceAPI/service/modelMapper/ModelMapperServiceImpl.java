package com.ecommerceAPI.service.modelMapper;

import com.ecommerceAPI.dto.response.CategoryResponse;
import com.ecommerceAPI.dto.response.ProductResponse;
import com.ecommerceAPI.entity.Category;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMapperServiceImpl implements ModelMapperService {
    private final ModelMapper modelMapper;

    public ModelMapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }

    public CategoryResponse mapCategoryWithProducts(Category category) {
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        List<ProductResponse> productResponseDTOList = category.getProducts().stream().map(product -> modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());

        categoryResponse.setProductList(productResponseDTOList);
        return categoryResponse;
    }
}
