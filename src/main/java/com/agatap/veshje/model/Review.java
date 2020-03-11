package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Integer id;
    private String comment;
    private int rate;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private User userId;
//    private Product productId;
}
