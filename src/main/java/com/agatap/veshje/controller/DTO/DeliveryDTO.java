package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String timeDelivery;
    private String description;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> ordersIds;
}
