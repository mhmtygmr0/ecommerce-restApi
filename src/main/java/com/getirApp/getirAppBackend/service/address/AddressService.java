package com.getirApp.getirAppBackend.service.address;

import com.getirApp.getirAppBackend.entity.Address;

import java.util.List;

public interface AddressService {
    Address save(Address address);

    Address get(long id);

    List<Address> getAll();

    Address update(Address address);

    void delete(long id);
}
