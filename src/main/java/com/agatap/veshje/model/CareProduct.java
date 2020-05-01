package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CareProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @Builder.Default
    @ManyToMany
    private List<Product> products = new ArrayList<>();
    @Builder.Default
    @ManyToMany(mappedBy = "cares")
    private List<Image> images = new ArrayList<>();
}
