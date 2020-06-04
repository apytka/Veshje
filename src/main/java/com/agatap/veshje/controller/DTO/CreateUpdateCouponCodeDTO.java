package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateCouponCodeDTO {
    private String code;
    private Double percentDiscount;
    private String description;
    private OffsetDateTime startDiscount;
    private OffsetDateTime expireDiscount;

    List<Integer> ordersId;
}
