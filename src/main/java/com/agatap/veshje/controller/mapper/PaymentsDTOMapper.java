package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsDTO;
import com.agatap.veshje.controller.DTO.PaymentsDTO;
import com.agatap.veshje.model.Payments;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentsDTOMapper {
    public PaymentsDTO mappingToDTO(Payments payments) {
        Integer typePaymentId = Optional.ofNullable(payments.getTypePayment())
                .map(p -> payments.getId()).orElse(null);
        Integer orderId = Optional.ofNullable(payments.getOrders())
                .map(p -> payments.getId()).orElse(null);
        Integer userId = Optional.ofNullable(payments.getUsers())
                .map(p -> payments.getId()).orElse(null);
        return PaymentsDTO.builder()
                .id(payments.getId())
                .amount(payments.getAmount())
                .paymentStatus(payments.getPaymentStatus())
                .paymentTypeIds(typePaymentId)
                .ordersIds(orderId)
                .userIds(userId)
                .createDate(payments.getCreateDate())
                .updateDate(payments.getUpdateDate())
                .build();
    }

    public Payments mappingToModel(CreateUpdatePaymentsDTO createPaymentsDTO) {
        return Payments.builder()
                .amount(createPaymentsDTO.getAmount())
                .paymentStatus(createPaymentsDTO.getPaymentStatus())
                .build();
    }
}
