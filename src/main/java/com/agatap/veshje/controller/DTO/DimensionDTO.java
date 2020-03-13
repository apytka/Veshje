package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDTO {
    private Integer id;
    private Integer bust;
    private Integer waist;
    private Integer hips;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer sizeId;
}
