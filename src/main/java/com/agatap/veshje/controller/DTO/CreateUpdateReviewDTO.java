package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.SizeType;
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
    private SizeType sizeType;

    private String productId;
    private Integer userId;
}
