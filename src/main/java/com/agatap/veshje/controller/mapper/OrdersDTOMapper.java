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
        Integer userId = Optional.ofNullable(orders.getUser())
                .map(user -> user.getId()).orElse(null);
        List<String> productsId = orders.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        List<Integer> orderItemsId = orders.getOrderItem().stream()
                .map(orderItem -> orderItem.getId())
                .collect(Collectors.toList());
        Integer orderAddressDataId = Optional.ofNullable(orders.getOrderAddressData())
                .map(orderAddressData -> orderAddressData.getId()).orElse(null);
        Integer couponCodeId = Optional.ofNullable(orders.getCouponsCode())
                .map(coupon -> coupon.getId()).orElse(null);
        return OrdersDTO.builder()
                .id(orders.getId())
                .orderStatus(orders.getOrderStatus())
                .totalProducts(orders.getTotalProducts())
                .discount(orders.getDiscount())
                .totalAmount(orders.getTotalAmount())
                .paymentId(paymentId)
                .paymentType(orders.getPaymentType())
                .deliveryId(deliveryId)
                .deliveryType(orders.getDeliveryType())
                .deliveryPrice(orders.getDeliveryPrice())
                .userId(userId)
                .productIds(productsId)
                .orderItemIds(orderItemsId)
                .orderAddressDataId(orderAddressDataId)
                .couponCodeId(couponCodeId)
                .createDate(orders.getCreateDate())
                .updateDate(orders.getCreateDate())
                .build();
    }

    public Orders mappingToModel(CreateUpdateOrdersDTO createOrdersDTO) {
        return Orders.builder()
                .orderStatus(createOrdersDTO.getOrderStatus())
                .totalAmount(createOrdersDTO.getTotalAmount())
                .paymentType(createOrdersDTO.getPaymentType())
                .deliveryType(createOrdersDTO.getDeliveryType())
                .deliveryPrice(createOrdersDTO.getDeliveryPrice())
                .build();
    }
}
