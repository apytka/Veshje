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
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private SizeType sizeType;
    private Integer quantity; //??
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToMany(mappedBy = "sizes")
    @Builder.Default
    private List<Product> products = new ArrayList<>();

}
