package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateOrderItemDTO;
import com.agatap.veshje.controller.DTO.OrderItemDTO;
import com.agatap.veshje.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderItemMapper {
    public OrderItemDTO mappingToDTO(OrderItem orderItem) {
        Integer orderId = Optional.ofNullable(orderItem.getOrder())
                .map(orders -> orders.getId()).orElse(null);
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .productPrice(orderItem.getProductPrice())
                .size(orderItem.getSize())
                .quantity(orderItem.getQuantity())
                .orderId(orderId)
                .createDate(orderItem.getCreateDate())
                .updateDate(orderItem.getUpdateDate())
                .build();
    }

    public OrderItem mappingToModel(CreateUpdateOrderItemDTO createUpdateOrderItemDTO) {
        return OrderItem.builder()
                .productId(createUpdateOrderItemDTO.getProductId())
                .productName(createUpdateOrderItemDTO.getProductName())
                .productPrice(createUpdateOrderItemDTO.getProductPrice())
                .size(createUpdateOrderItemDTO.getSize())
                .quantity(createUpdateOrderItemDTO.getQuantity())
                .build();
    }
}
