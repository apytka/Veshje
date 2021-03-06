package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.CompositionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CompositionProductDTO {
    private Integer id;
    private CompositionType compositionType;
    private String description;
    private Integer compositionPercent;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<String> productsIds;
}