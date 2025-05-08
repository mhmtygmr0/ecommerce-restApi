package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.response.DeliveryResponse;
import com.ecommerceAPI.entity.Delivery;
import com.ecommerceAPI.service.delivery.DeliveryService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final ModelMapperService modelMapper;

    public DeliveryController(DeliveryService deliveryService, ModelMapperService modelMapper) {
        this.deliveryService = deliveryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Delivery delivery = this.deliveryService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(delivery, DeliveryResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Delivery> deliveryList = this.deliveryService.getAll();
        List<DeliveryResponse> deliveryResponseList = deliveryList.stream().map(delivery -> this.modelMapper.forResponse().map(delivery, DeliveryResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(deliveryResponseList));
    }

    @PutMapping("/delivered/{id}")
    public ResponseEntity<Map<String, Object>> markAsDelivered(@PathVariable("id") Long deliveryId) {
        Delivery delivery = this.deliveryService.getById(deliveryId);
        this.deliveryService.markAsDelivered(deliveryId);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(delivery, DeliveryResponse.class)));
    }
}
