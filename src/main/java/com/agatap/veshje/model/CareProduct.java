package com.agatap.veshje.model;

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
public class CareProduct {
    private Integer id;
    private String name;
    private String description;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<Product> productList;
//    private List<Picture> pictureList;  //???
}
