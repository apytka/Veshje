package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateOrdersDTO {
    private OrderStatus orderStatus;

    private Integer paymentIds;
    private Integer deliveryIds;
    private Integer ordersIds;
}
