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
public class ReviewDTO {
    private Integer id;
    private String comment;
    private int rate;
    private int rateSize;
    private int rateLength;
    private SizeType sizeType;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private String productIds;
    private Integer userId;
}
