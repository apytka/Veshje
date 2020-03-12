package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Size;
import com.agatap.veshje.model.TypeCollection;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private BigDecimal price;
    private String description;
    private Size size;
    private TypeCollection typeCollection;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> orderIds;
    private List<Integer> compositionIds;
    private List<Integer> categoriesIds;
    private List<Integer> reviewIds;
    private Integer careProductIds;
}
