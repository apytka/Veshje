package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CompositionProductDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCompositionProductDTO;
import com.agatap.veshje.model.CompositionProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompositionProductDTOMapper {
    public CompositionProductDTO mappingToDTO(CompositionProduct compositionProduct) {
        List<Integer> productsId = compositionProduct.getProducts().stream()
                .map(product -> compositionProduct.getId())
                .collect(Collectors.toList());
        return CompositionProductDTO.builder()
                .id(compositionProduct.getId())
                .compositionType(compositionProduct.getCompositionType())
                .description(compositionProduct.getDescription())
                .compositionPercent(compositionProduct.getCompositionPercent())
                .productsIds(productsId)
                .createDate(compositionProduct.getCreateDate())
                .updateDate(compositionProduct.getUpdateDate())
                .build();
    }

    public CompositionProduct mappingToModel(CreateUpdateCompositionProductDTO createCompositionProductDTO) {
        return CompositionProduct.builder()
                .compositionType(createCompositionProductDTO.getCompositionType())
                .description(createCompositionProductDTO.getDescription())
                .compositionPercent(createCompositionProductDTO.getCompositionPercent())
                .build();
    }
}
