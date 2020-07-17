package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String comment;
    private int rate;
    private int rateSize;
    private int rateLength;
    private SizeType sizeType;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;
}
