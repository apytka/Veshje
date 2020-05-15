package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateOrderItemDTO {
    private String productId;
    private String productName;
    private Double productPrice;
    private String size;
    private Integer quantity;

    private Integer orderId;
}
