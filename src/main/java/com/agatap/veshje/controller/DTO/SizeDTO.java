package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.SizeType;
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
public class SizeDTO {
    private Integer id;
    private SizeType sizeType;
    private Integer quantity; //??
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> productsIds;
}
