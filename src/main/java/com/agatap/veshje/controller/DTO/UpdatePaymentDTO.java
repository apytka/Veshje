package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.PaymentsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentDTO {
    private PaymentsStatus paymentStatus;

    private List<Integer> paymentIds;
}
