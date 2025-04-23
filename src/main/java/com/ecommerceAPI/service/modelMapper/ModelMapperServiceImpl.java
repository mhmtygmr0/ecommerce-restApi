package com.ecommerceAPI.service.modelMapper;

import com.ecommerceAPI.dto.response.OrderItemResponse;
import com.ecommerceAPI.entity.OrderItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

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

        this.modelMapper.typeMap(OrderItem.class, OrderItemResponse.class)
                .addMapping(src -> src.getProduct().getName(), OrderItemResponse::setProductName)
                .addMapping(src -> src.getProduct().getPrice(), OrderItemResponse::setProductPrice)
                .addMapping(src -> src.getProduct().getImageUrl(), OrderItemResponse::setProductImageUrl);

        return this.modelMapper;
    }
}
