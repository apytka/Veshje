package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String productId;
    private String productName;
    private Double productPrice;
    private SizeType sizeType;
    private Integer quantity;
    private boolean addReview;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private Orders order;

}
