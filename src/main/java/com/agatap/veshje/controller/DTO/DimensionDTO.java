package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.SizeType;
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
    private SizeType sizeType;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

}
