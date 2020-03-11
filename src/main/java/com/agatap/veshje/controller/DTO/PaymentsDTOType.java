package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDTOType {
    private String name;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> paymentIds;
}
