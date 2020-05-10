package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.OrderStatus;
import com.agatap.veshje.model.User;
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
public class OrdersDTO {
    private Integer id;
    private OrderStatus orderStatus;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer paymentIds;
    private Integer deliveryIds;
    private Integer userOrdersIds;
    private List<String> productIds;
}
