package com.ecommerceAPI.service.address;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Address;
import com.ecommerceAPI.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address save(Address address) {
        address.setId(null);
        return addressRepository.save(address);
    }

    @Override
    public Address getById(long id) {
        return this.addressRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Address> getAll() {
        return this.addressRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Address update(Address address) {
        Address existingAddress = this.getById(address.getId());
        if (address.getUser() == null) {
            address.setUser(existingAddress.getUser());
        }
        return this.addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Address address = this.getById(id);
        this.addressRepository.delete(address);
    }
}
