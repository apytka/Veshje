package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateOrdersDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.model.Orders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrdersDTOMapper {
    public OrdersDTO mappingToDTO(Orders orders) {
        Integer paymentId = Optional.ofNullable(orders.getPayment())
                .map(payment -> payment.getId()).orElse(null);
        Integer deliveryId = Optional.ofNullable(orders.getDelivery())
                .map(delivery -> delivery.getId()).orElse(null);
        Integer userId = Optional.ofNullable(orders.getUserOrders())
                .map(user -> user.getId()).orElse(null);
        List<String> productsId = orders.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        return OrdersDTO.builder()
                .id(orders.getId())
                .orderStatus(orders.getOrderStatus())
                .paymentIds(paymentId)
                .deliveryIds(deliveryId)
                .userOrdersIds(userId)
                .productIds(productsId)
                .createDate(orders.getCreateDate())
                .updateDate(orders.getCreateDate())
                .build();
    }

    public Orders mappingToModel(CreateUpdateOrdersDTO createOrdersDTO) {
        return Orders.builder()
                .orderStatus(createOrdersDTO.getOrderStatus())
                .build();
    }
}
