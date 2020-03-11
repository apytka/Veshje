package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private OrderStatus orderStatus;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private Delivery deliveryId;
//    private User userId;
//    private Payment paymentId;
//    private Integer List<Product> products;
}
