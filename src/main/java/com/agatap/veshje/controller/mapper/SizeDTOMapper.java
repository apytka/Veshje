package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateSizeDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.model.Size;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SizeDTOMapper {
    public SizeDTO mappingToDTO(Size size) {
        String productsId = Optional.ofNullable(size.getProduct())
                .map(product -> product.getId()).orElse(null);
        return SizeDTO.builder()
                .id(size.getId())
                .sizeType(size.getSizeType())
                .quantity(size.getQuantity())
                .productId(productsId)
                .createDate(size.getCreateDate())
                .updateDate(size.getUpdateDate())
                .build();
    }

    public Size mappingToModel(CreateUpdateSizeDTO createSizeDTO) {
        return Size.builder()
                .sizeType(createSizeDTO.getSizeType())
                .quantity(createSizeDTO.getQuantity())
                .build();
    }
}
