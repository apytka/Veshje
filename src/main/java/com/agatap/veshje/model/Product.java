package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal price;
    private String picture;  //???
    private String description;
    private Size size;
    private TypeCollection typeCollection;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<Picture> pictureList;
//    private List<CompositionProduct> compositionList;
//    private List<CareProduct> detailsProductList;
//    private List<Category> categoryList;
//    private List<Product> productList;
//    private List<Review> reviewList;
}
