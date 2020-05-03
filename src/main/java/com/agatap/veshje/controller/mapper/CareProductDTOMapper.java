package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CareDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCareDTO;
import com.agatap.veshje.model.Care;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CareProductDTOMapper {
    public CareDTO mappingToDTO(Care careProduct) {
        List<Integer> productsId = careProduct.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        List<Integer> imagesId = careProduct.getImages().stream()
                .map(image -> image.getId())
                .collect(Collectors.toList());
        return CareDTO.builder()
                .id(careProduct.getId())
                .name(careProduct.getName())
                .description(careProduct.getDescription())
                .productIds(productsId)
                .imageIds(imagesId)
                .createDate(careProduct.getCreateDate())
                .updateDate(careProduct.getUpdateDate())
                .build();
    }

    public Care mappingToModel(CreateUpdateCareDTO createCareProductDTO) {
        return Care.builder()
                .name(createCareProductDTO.getName())
                .description(createCareProductDTO.getDescription())
                .build();
    }
}

