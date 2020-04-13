package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.AddressDataDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateAddressDataDTO;
import com.agatap.veshje.model.AddressData;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressDataDTOMapper {
    public AddressDataDTO mappingToDTO(AddressData addressData) {
        Integer userId = Optional.ofNullable(addressData.getUser())
                .map(user -> user.getId()).orElse(null);
        return AddressDataDTO.builder()
                .id(addressData.getId())
                .firstName(addressData.getFirstName())
                .lastName(addressData.getLastName())
                .phoneNumber(addressData.getPhoneNumber())
                .street(addressData.getStreet())
                .no(addressData.getNo())
                .postalCode(addressData.getPostalCode())
                .city(addressData.getCity())
                .information(addressData.getInformation())
                .userId(userId)
                .createDate(addressData.getCreateDate())
                .updateDate(addressData.getUpdateDate())
                .build();
    }

    public AddressData mappingToModel(CreateUpdateAddressDataDTO createUpdateAddressDataDTO) {
        return AddressData.builder()
                .firstName(createUpdateAddressDataDTO.getFirstName())
                .lastName(createUpdateAddressDataDTO.getLastName())
                .phoneNumber(createUpdateAddressDataDTO.getPhoneNumber())
                .street(createUpdateAddressDataDTO.getStreet())
                .no(createUpdateAddressDataDTO.getNo())
                .postalCode(createUpdateAddressDataDTO.getPostalCode())
                .city(createUpdateAddressDataDTO.getCity())
                .information(createUpdateAddressDataDTO.getInformation())
                .build();
    }
}
