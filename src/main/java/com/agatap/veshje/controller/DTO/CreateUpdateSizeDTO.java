package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.SizeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateSizeDTO {
    private SizeType sizeType;
    private Integer quantity; //??
    private String dimensionBust;
    private String dimensionWaist;
    private String dimensionHips;

    private List<Integer> productsIds;
    private Integer sizeId;
}
