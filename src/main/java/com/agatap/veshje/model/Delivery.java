package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    private String name;
    private BigDecimal price;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private Order orderId;
}
