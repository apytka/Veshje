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
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fileName;
    private String fileType;
    private long size;
    @Lob
    private byte[] data;

    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private Product product;
    @ManyToMany
    @Builder.Default
    private List<CareProduct> cares = new ArrayList<>();

}
