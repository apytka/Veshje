package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositionProductDTO {
    private MaterialType compositionType;
    private String description;
    private int compositionPercent;
}