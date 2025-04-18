package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.AddressRequest;
import com.ecommerceAPI.dto.response.AddressResponse;
import com.ecommerceAPI.entity.Address;
import com.ecommerceAPI.service.address.AddressService;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    private final ModelMapperService modelMapper;

    public AddressController(AddressService addressService, ModelMapperService modelMapper) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody AddressRequest addressRequest) {
        Address address = this.modelMapper.forRequest().map(addressRequest, Address.class);
        this.addressService.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultHelper.created(this.modelMapper.forResponse().map(address, AddressResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        Address address = this.addressService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(address, AddressResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Address> addressList = this.addressService.getAll();
        List<AddressResponse> addressResponseList = addressList.stream().map(address -> this.modelMapper.forResponse().map(address, AddressResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(addressResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody AddressRequest addressRequest) {
        Address address = this.modelMapper.forRequest().map(addressRequest, Address.class);
        address.setId(id);
        this.addressService.update(address);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(address, AddressResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.addressService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
