package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.OrderAddressData;
import com.agatap.veshje.model.OrderItem;
import com.agatap.veshje.model.OrderStatus;
import com.agatap.veshje.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;
import java.time.OffsetDateTime;
import java.util.ArrayList;
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
    private Double totalAmount;

    private Integer paymentId;
    private String paymentType;
    private Integer deliveryId;
    private Integer addressDataId;
    private String deliveryType;
    private Double deliveryPrice;
    private Integer userId;
    private List<String> productIds;
    private List<Integer> orderItemIds;
    private Integer orderAddressDataId;
    private Integer couponCodeId;
}
