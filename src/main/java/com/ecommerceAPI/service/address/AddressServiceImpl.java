package com.ecommerceAPI.service.address;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Address;
import com.ecommerceAPI.repository.AddressRepository;
import com.ecommerceAPI.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public AddressServiceImpl(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Address save(Address address) {
        address.setId(null);
        this.userService.getById(address.getUser().getId());
        return this.addressRepository.save(address);
    }

    @Override
    public Address getById(Long id) {
        return this.addressRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Address"));
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
    public void delete(Long id) {
        Address address = this.getById(id);
        this.addressRepository.delete(address);
    }
}
