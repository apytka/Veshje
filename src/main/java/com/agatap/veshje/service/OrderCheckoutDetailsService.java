package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.OrderCheckoutDetailsDTO;
import com.agatap.veshje.controller.DTO.UpdateOrderCheckoutDetailsDTO;
import com.agatap.veshje.controller.DTO.UpdateOrderCheckoutDetailsDeliveryDTO;
import com.agatap.veshje.model.OrderCheckoutDetails;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderCheckoutDetailsService {
    private OrderCheckoutDetails orderCheckoutDetails = new OrderCheckoutDetails();

    @PostConstruct
    public void init() {
        orderCheckoutDetails.setAddressId(null);
        orderCheckoutDetails.setDeliveryId(null);
        orderCheckoutDetails.setPaymentId(null);
    }

    public OrderCheckoutDetailsDTO getOrderCheckoutDetailsDTO() {
        return mappingToDTO(orderCheckoutDetails);
    }

    public OrderCheckoutDetails getOrderCheckoutDetails() {
        return orderCheckoutDetails;
    }


    public OrderCheckoutDetailsDTO updateOrderCheckoutDetails(UpdateOrderCheckoutDetailsDTO updateOrderCheckoutDetailsDTO) {
        OrderCheckoutDetails updateOrderCheckoutDetails = getOrderCheckoutDetails();
        updateOrderCheckoutDetails.setAddressId(updateOrderCheckoutDetailsDTO.getAddressId());
        updateOrderCheckoutDetails.setPaymentId(updateOrderCheckoutDetailsDTO.getPaymentId());
        return mappingToDTO(updateOrderCheckoutDetails);
    }

    private OrderCheckoutDetailsDTO mappingToDTO(OrderCheckoutDetails orderCheckoutDetails) {
        return OrderCheckoutDetailsDTO.builder()
                .addressId(orderCheckoutDetails.getAddressId())
                .deliveryId(orderCheckoutDetails.getDeliveryId())
                .paymentId(orderCheckoutDetails.getPaymentId())
                .build();
    }

    public OrderCheckoutDetailsDTO updateOrderCheckoutDetailsDelivery(UpdateOrderCheckoutDetailsDeliveryDTO updateOrderCheckoutDetailsDeliveryDTO) {
        OrderCheckoutDetails updateOrderCheckoutDetails = getOrderCheckoutDetails();
        updateOrderCheckoutDetails.setDeliveryId(updateOrderCheckoutDetailsDeliveryDTO.getDeliveryId());
        return mappingToDTO(updateOrderCheckoutDetails);
    }
}
