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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private long size;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private Product product;
    @ManyToMany
    @Builder.Default
    private List<CareProduct> cares = new ArrayList<>();
}
