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
public class CareProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToOne
    private Product product;
    @ManyToMany(mappedBy = "cares")
    @Builder.Default
    private List<Picture> pictures = new ArrayList<>();
}
