package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.TypeCollection;
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
    private Integer id;
    private String name;
    private Double price;
    private String color;
    private String description;
    private String complementDescription;
    private TypeCollection typeCollection;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> orderIds;
    private List<Integer> compositionIds;
    private List<Integer> categoriesIds;
    private List<Integer> reviewIds;
    private List<Integer> careIds;
    private List<Integer> imagesIds;
    private List<Integer> sizeIds;
    private List<Integer> favouritesIds;
}
