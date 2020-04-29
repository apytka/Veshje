package com.agatap.veshje.controller.mapper;

        import com.agatap.veshje.controller.DTO.AddressDTO;
        import com.agatap.veshje.controller.DTO.CreateUpdateAddressDTO;
        import com.agatap.veshje.model.Address;
        import org.springframework.stereotype.Component;

        import java.util.List;
        import java.util.Optional;
        import java.util.stream.Collectors;

@Component
public class AddressDTOMapper {

    public AddressDTO mappingToDTO(Address address) {
        Integer cityId = Optional.ofNullable(address.getCity())
                .map(city -> city.getId()).orElse(null);
        Integer storeId = Optional.ofNullable(address.getStore())
                .map(story -> story.getId()).orElse(null);
        List<Integer> usersId = address.getUsers().stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .no(address.getNo())
                .postalCode(address.getPostalCode())
                .phoneNumber(address.getPhoneNumber())
                .cityId(cityId)
                .storeId(storeId)
                .usersId(usersId)
                .createDate(address.getCreateDate())
                .updateDate(address.getUpdateDate())
                .build();
    }

    public Address mappingToModel(CreateUpdateAddressDTO createAddressDTO) {
        return Address.builder()
                .street(createAddressDTO.getStreet())
                .no(createAddressDTO.getNo())
                .postalCode(createAddressDTO.getPostalCode())
                .phoneNumber(createAddressDTO.getPhoneNumber())
                .build();
    }
}
