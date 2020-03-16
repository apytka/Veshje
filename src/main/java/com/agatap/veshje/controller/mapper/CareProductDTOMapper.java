package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CareProductDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCareProductDTO;
import com.agatap.veshje.model.CareProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CareProductDTOMapper {
    public CareProductDTO mappingToDTO(CareProduct careProduct) {
        Integer productId = Optional.ofNullable(careProduct.getProduct())
                .map(product -> careProduct.getId()).orElse(null);
        List<Integer> picturesId = careProduct.getImages().stream()
                .map(picture -> careProduct.getId())
                .collect(Collectors.toList());
        return CareProductDTO.builder()
                .id(careProduct.getId())
                .name(careProduct.getName())
                .description(careProduct.getDescription())
                .productIds(productId)
                .pictureIds(picturesId)
                .createDate(careProduct.getCreateDate())
                .updateDate(careProduct.getUpdateDate())
                .build();
    }

    public CareProduct mappingToModel(CreateUpdateCareProductDTO createCareProductDTO) {
        return CareProduct.builder()
                .name(createCareProductDTO.getName())
                .description(createCareProductDTO.getDescription())
                .build();
    }
}

