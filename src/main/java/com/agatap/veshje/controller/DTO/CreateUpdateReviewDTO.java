package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateReviewDTO {
    private String comment;
    private Integer rate;
    private int rateSize;
    private int rateLength;

    private String productId;
    private Integer userId;
}
