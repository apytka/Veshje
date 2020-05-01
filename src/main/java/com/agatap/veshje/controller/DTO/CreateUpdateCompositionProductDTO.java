package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.CompositionType;
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
    private CompositionType compositionType;
    private String description;
    private Integer compositionPercent;

    private List<Integer> productsIds;
}
