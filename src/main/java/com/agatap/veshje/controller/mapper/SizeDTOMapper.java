package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateSizeDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.model.Size;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SizeDTOMapper {
    public SizeDTO mappingToDTO(Size size) {
        List<Integer> productsId = size.getProducts().stream()
                .map(product -> size.getId())
                .collect(Collectors.toList());
        Integer dimensionId = Optional.ofNullable(size.getDimension())
                .map(dimension -> size.getId()).orElse(null);
        return SizeDTO.builder()
                .id(size.getId())
                .sizeType(size.getSizeType())
                .quantity(size.getQuantity())
                .productsIds(productsId)
                .dimensionIds(dimensionId)
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
