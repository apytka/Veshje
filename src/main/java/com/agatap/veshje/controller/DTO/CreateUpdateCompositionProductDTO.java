package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateCompositionProductDTO {
    private MaterialType compositionType;
    private String description;
    private int compositionPercent;

    private List<Integer> productsIds;
}
