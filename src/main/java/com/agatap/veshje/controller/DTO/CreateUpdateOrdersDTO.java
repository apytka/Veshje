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
    private Double totalProducts;
    private Double discount;
    private Double totalAmount;

    private Integer paymentId;
    private String paymentType;
    private Integer deliveryId;
    private String deliveryType;
    private Double deliveryPrice;
    private Integer userId;
    private List<String> productIds;
    private List<Integer> orderItemIds;
    private Integer orderAddressDataId;
    private Integer couponCodeId;
}
