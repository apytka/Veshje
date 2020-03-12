package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateStoreDTO;
import com.agatap.veshje.controller.DTO.StoreDTO;
import com.agatap.veshje.model.Store;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StoreDTOMapper {

    public StoreDTO mappingToDTO(Store store) {
        Integer addressId = Optional.ofNullable(store.getAddress())
                .map(s -> store.getId()).orElse(null);
        return StoreDTO.builder()
                .name(store.getName())
                .addressIds(addressId)
                .createDate(store.getCreateDate())
                .updateDate(store.getUpdateDate())
                .build();
    }

    public Store mappingToModel(CreateUpdateStoreDTO createStoreDTO) {
        return Store.builder()
                .name(createStoreDTO.getName())
                .build();
    }
}
