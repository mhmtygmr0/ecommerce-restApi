package com.ecommerceAPI.service.address;

import com.ecommerceAPI.entity.Address;

import java.util.List;

public interface AddressService {
    Address save(Address address);

    Address getById(Long id);

    List<Address> getAll();

    Address update(Address address);

    void delete(Long id);
}
