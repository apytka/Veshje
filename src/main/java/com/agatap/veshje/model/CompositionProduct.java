package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositionProduct {
    private Integer id;
    private String description;
    private MaterialType compositionType;
    private int compositionPercent;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<Product> productList;
}
