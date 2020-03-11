package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.PaymentsStatus;
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
public class PaymentsDTO {
    private BigDecimal amount;
    private PaymentsStatus paymentStatus;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer paymentTypeIds;
    private Integer ordersIds;
    private Integer userIds;
}
