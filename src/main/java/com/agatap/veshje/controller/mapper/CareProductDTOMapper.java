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
        List<Integer> productsId = careProduct.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        List<Integer> imagesId = careProduct.getImages().stream()
                .map(image -> image.getId())
                .collect(Collectors.toList());
        return CareProductDTO.builder()
                .id(careProduct.getId())
                .name(careProduct.getName())
                .description(careProduct.getDescription())
                .productIds(productsId)
                .imageIds(imagesId)
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

