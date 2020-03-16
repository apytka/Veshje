package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private TypeCollection typeCollection;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private List<Orders> orders = new ArrayList<>();
    @ManyToMany
    @Builder.Default
    private List<CompositionProduct> composition = new ArrayList<>();
    @ManyToMany
    @Builder.Default
    private List<Category> categories = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    @OneToOne(mappedBy = "product")
    private CareProduct careProduct;
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<Image> images = new ArrayList<>();
    @ManyToMany
    @Builder.Default
    private List<Size> sizes = new ArrayList<>();
}
