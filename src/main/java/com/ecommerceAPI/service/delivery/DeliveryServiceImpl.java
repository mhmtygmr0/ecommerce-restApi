package com.ecommerceAPI.service.delivery;

import com.ecommerceAPI.core.exception.NotFoundException;
import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.entity.Delivery;
import com.ecommerceAPI.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Delivery save(Delivery delivery) {
        delivery.setId(null);
        return this.deliveryRepository.save(delivery);
    }

    @Override
    public Delivery getById(Long id) {
        return this.deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND, "Delivery"));
    }

    @Override
    public List<Delivery> getAll() {
        return this.deliveryRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Delivery update(Delivery delivery) {
        this.getById(delivery.getId());
        return this.deliveryRepository.save(delivery);
    }

    @Override
    public void delete(Long id) {
        Delivery delivery = this.getById(id);
        this.deliveryRepository.delete(delivery);
    }
}
