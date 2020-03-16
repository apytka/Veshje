package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateDeliveryDTO;
import com.agatap.veshje.controller.DTO.DeliveryDTO;
import com.agatap.veshje.model.Delivery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryDTOMapper {
    public DeliveryDTO mappingToDTO(Delivery delivery) {
        List<Integer> ordersId = delivery.getOrders().stream()
                .map(order -> delivery.getId())
                .collect(Collectors.toList());
        return DeliveryDTO.builder()
                .id(delivery.getId())
                .name(delivery.getName())
                .price(delivery.getPrice())
                .ordersIds(ordersId)
                .createDate(delivery.getCreateDate())
                .updateDate(delivery.getUpdateDate())
                .build();
    }

    public Delivery mappingToModel(CreateUpdateDeliveryDTO createDeliveryDTO) {
        return Delivery.builder()
                .name(createDeliveryDTO.getName())
                .price(createDeliveryDTO.getPrice())
                .build();
    }
}
