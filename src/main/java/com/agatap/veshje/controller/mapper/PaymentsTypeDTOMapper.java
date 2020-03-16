package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsTypeDTO;
import com.agatap.veshje.controller.DTO.PaymentsTypeDTO;
import com.agatap.veshje.model.PaymentsType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentsTypeDTOMapper {

    public PaymentsTypeDTO mappingToDTO(PaymentsType paymentsTypes) {
        List<Integer> paymentsId = paymentsTypes.getPayments().stream()
                .map(payment -> paymentsTypes.getId())
                .collect(Collectors.toList());
        return PaymentsTypeDTO.builder()
                .id(paymentsTypes.getId())
                .name(paymentsTypes.getName())
                .paymentIds(paymentsId)
                .createDate(paymentsTypes.getCreateDate())
                .updateDate(paymentsTypes.getUpdateDate())
                .build();
    }

    public PaymentsType mappingToModel(CreateUpdatePaymentsTypeDTO createPaymentsTypeDTO) {
        return PaymentsType.builder()
                .name(createPaymentsTypeDTO.getName())
                .build();
    }
}
