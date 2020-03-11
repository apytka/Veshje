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
public class Payment {
    private Integer id;
    private BigDecimal amount;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private User userId;
//    private Order orderId;
}
