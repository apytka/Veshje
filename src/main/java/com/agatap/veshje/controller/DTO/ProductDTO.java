package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Size;
import com.agatap.veshje.model.TypeCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private BigDecimal price;
    private String description;
    private Size size;
    private TypeCollection typeCollection;
}
