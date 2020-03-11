package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
}
