package com.getirApp.getirAppBackend.service.address;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.Address;
import com.getirApp.getirAppBackend.repository.AddressRepository;
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
    public Address get(long id) {
        return this.addressRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<Address> getAll() {
        return this.addressRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Address update(Address address) {
        this.get(address.getId());
        return this.addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Address address = this.get(id);
        this.addressRepository.delete(address);
    }
}
