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
public class StoreDTO {
    private Integer id;
    private String name;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer addressIds;
}
