package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.AddressRequest;
import com.getirApp.getirAppBackend.dto.response.AddressResponse;
import com.getirApp.getirAppBackend.entity.Address;
import com.getirApp.getirAppBackend.service.address.AddressService;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AddressResponse> save(@Valid @RequestBody AddressRequest addressRequest) {
        Address address = this.modelMapper.forRequest().map(addressRequest, Address.class);
        this.addressService.save(address);
        return ResultHelper.created(this.modelMapper.forResponse().map(address, AddressResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AddressResponse> get(@PathVariable("id") Long id) {
        Address address = this.addressService.get(id);
        return ResultHelper.success(modelMapper.forResponse().map(address, AddressResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AddressResponse>> getAll() {
        List<Address> addressList = this.addressService.getAll();
        List<AddressResponse> addressResponseList = addressList.stream().map(address -> modelMapper.forResponse().map(address, AddressResponse.class)).toList();
        return ResultHelper.success(addressResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AddressResponse> update(@PathVariable Long id, @Valid @RequestBody AddressRequest addressRequest) {
        Address address = this.modelMapper.forRequest().map(addressRequest, Address.class);
        address.setId(id);
        this.addressService.update(address);
        return ResultHelper.success(this.modelMapper.forResponse().map(address, AddressResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.addressService.delete(id);
        return ResultHelper.ok();
    }
}
