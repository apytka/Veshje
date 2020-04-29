package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateDimensionDTO;
import com.agatap.veshje.controller.DTO.DimensionDTO;
import com.agatap.veshje.model.Dimension;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DimensionDTOMapper {
    public DimensionDTO mappingToDTO(Dimension dimension) {
        Integer sizeId = Optional.ofNullable(dimension.getSize())
                .map(size -> size.getId()).orElse(null);
        return DimensionDTO.builder()
                .id(dimension.getId())
                .bust(dimension.getBust())
                .waist(dimension.getWaist())
                .hips(dimension.getHips())
                .sizeId(sizeId)
                .createDate(dimension.getCreateDate())
                .updateDate(dimension.getUpdateDate())
                .build();
    }

    public Dimension mappingToModel(CreateUpdateDimensionDTO createDimensionDTO) {
        return Dimension.builder()
                .bust(createDimensionDTO.getBust())
                .waist(createDimensionDTO.getWaist())
                .hips(createDimensionDTO.getHips())
                .build();
    }
}
