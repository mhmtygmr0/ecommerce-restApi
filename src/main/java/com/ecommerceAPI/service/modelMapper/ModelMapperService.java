package com.ecommerceAPI.service.modelMapper;

import com.ecommerceAPI.dto.response.OrderItemResponse;
import com.ecommerceAPI.entity.OrderItem;

public interface ModelMapperService {

    org.modelmapper.ModelMapper forRequest();

    org.modelmapper.ModelMapper forResponse();

    OrderItemResponse mapToResponse(OrderItem orderItem);
}
