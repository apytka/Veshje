package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private BigDecimal price;
    private String picture;  //???
    private String description;
    private Size size;
    private TypeCollection typeCollection;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

//    private List<CompositionProduct> compositionList;
//    private List<CareProduct> detailsProductList;
//    private List<Category> categoryList;
//    private List<Product> productList;
//    private List<Review> reviewList;
}
