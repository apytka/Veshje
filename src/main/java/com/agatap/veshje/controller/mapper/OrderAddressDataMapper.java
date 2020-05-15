package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateOrderAddressDataDTO;
import com.agatap.veshje.controller.DTO.OrderAddressDataDTO;
import com.agatap.veshje.model.OrderAddressData;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderAddressDataMapper {
    public OrderAddressDataDTO mappingToDTO(OrderAddressData orderAddressData) {
        Integer orderId = Optional.ofNullable(orderAddressData.getOrder())
                .map(orders -> orders.getId()).orElse(null);
        return OrderAddressDataDTO.builder()
                .id(orderAddressData.getId())
                .firstName(orderAddressData.getFirstName())
                .lastName(orderAddressData.getLastName())
                .phoneNumber(orderAddressData.getPhoneNumber())
                .street(orderAddressData.getStreet())
                .no(orderAddressData.getNo())
                .postalCode(orderAddressData.getPostalCode())
                .city(orderAddressData.getCity())
                .information(orderAddressData.getInformation())
                .orderId(orderId)
                .createDate(orderAddressData.getCreateDate())
                .updateDate(orderAddressData.getUpdateDate())
                .build();
    }

    public OrderAddressData mappingToModel(CreateUpdateOrderAddressDataDTO createUpdateOrderAddressDataDTO) {
        return OrderAddressData.builder()
                .firstName(createUpdateOrderAddressDataDTO.getFirstName())
                .lastName(createUpdateOrderAddressDataDTO.getLastName())
                .phoneNumber(createUpdateOrderAddressDataDTO.getPhoneNumber())
                .street(createUpdateOrderAddressDataDTO.getStreet())
                .no(createUpdateOrderAddressDataDTO.getNo())
                .postalCode(createUpdateOrderAddressDataDTO.getPostalCode())
                .city(createUpdateOrderAddressDataDTO.getCity())
                .information(createUpdateOrderAddressDataDTO.getInformation())
                .build();
    }
}
