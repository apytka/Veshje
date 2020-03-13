package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateDeliveryDTO;
import com.agatap.veshje.controller.DTO.DeliveryDTO;
import com.agatap.veshje.controller.mapper.DeliveryDTOMapper;
import com.agatap.veshje.model.Delivery;
import com.agatap.veshje.repository.DeliveryRepository;
import com.agatap.veshje.service.exception.DeliveryAlreadyExistsException;
import com.agatap.veshje.service.exception.DeliveryDataInvalidException;
import com.agatap.veshje.service.exception.DeliveryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryDTOMapper mapper;

    public List<DeliveryDTO> getAllDelivery() {
        return deliveryRepository.findAll().stream()
                .map(delivery -> mapper.mappingToDTO(delivery))
                .collect(Collectors.toList());
    }

    public DeliveryDTO findDeliveryDTOById(Integer id) throws DeliveryNotFoundException {
        return deliveryRepository.findById(id)
                .map(delivery -> mapper.mappingToDTO(delivery))
                .orElseThrow(() -> new DeliveryNotFoundException());
    }

    public Delivery findDeliveryOById(Integer id) throws DeliveryNotFoundException {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException());
    }

    public DeliveryDTO createDeliveryDTO(CreateUpdateDeliveryDTO createDeliveryDTO)
            throws DeliveryDataInvalidException, DeliveryAlreadyExistsException {
        if(deliveryRepository.existsByName(createDeliveryDTO.getName())) {
            throw new DeliveryAlreadyExistsException();
        }
        if(createDeliveryDTO.getName() == null || createDeliveryDTO.getPrice() == null
                || createDeliveryDTO.getPrice().doubleValue() <= 0) {
            throw new DeliveryDataInvalidException();
        }
        Delivery delivery = mapper.mappingToModel(createDeliveryDTO);
        delivery.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Delivery newDelivery = deliveryRepository.save(delivery);
        return mapper.mappingToDTO(newDelivery);
    }

    public DeliveryDTO updateDeliveryDTO(CreateUpdateDeliveryDTO updateDeliveryDTO, Integer id) throws DeliveryNotFoundException {
        Delivery delivery = findDeliveryOById(id);
        delivery.setName(updateDeliveryDTO.getName());
        delivery.setPrice(delivery.getPrice());
        delivery.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Delivery updateDelivery = deliveryRepository.save(delivery);
        return mapper.mappingToDTO(updateDelivery);
    }

    public DeliveryDTO deleteDeliveryDTO(Integer id) throws DeliveryNotFoundException {
        Delivery delivery = findDeliveryOById(id);
        deliveryRepository.delete(delivery);
        return mapper.mappingToDTO(delivery);
    }
}

