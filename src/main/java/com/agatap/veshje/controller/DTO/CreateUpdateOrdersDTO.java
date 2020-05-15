package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.OrderStatus;
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
public class CreateUpdateOrdersDTO {
    private OrderStatus orderStatus;
    private Double totalAmount;

    private Integer paymentIds;
    private String paymentType;
    private Integer deliveryIds;
    private String deliveryType;
    private Double deliveryPrice;
    private Integer userOrdersIds;
    private List<String> productIds;
    private List<Integer> orderItemIds;
    private Integer orderAddressDataId;
}
