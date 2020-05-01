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
public class CompositionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private CompositionType compositionType;
    private String description;
    private Integer compositionPercent;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToMany(mappedBy = "composition")
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
