package com.agatap.veshje.model;

import com.agatap.veshje.model.utils.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @GenericGenerator(
            name = "product_seq",
            strategy = "com.agatap.veshje.model.utils.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "YX001-"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d"),
                    })
    private String id;
    private String name;
    private Double price;
    private String color;
    private String description;
    private String complementDescription;
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
    @Builder.Default
    @ManyToMany(mappedBy = "products")
    private List<Care> cares = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<Image> images = new ArrayList<>();
    @ManyToMany
    @Builder.Default
    private List<Size> sizes = new ArrayList<>();
    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private List<Favourites> favourites = new ArrayList<>();
}
