package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.OrderCheckoutDetailsDTO;
import com.agatap.veshje.controller.DTO.UpdateOrderCheckoutDetailsDTO;
import com.agatap.veshje.controller.DTO.UpdateOrderCheckoutDetailsDeliveryDTO;
import com.agatap.veshje.service.OrderCheckoutDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-checkout-details")
public class OrderCheckoutDetailsController {

    @Autowired
    private OrderCheckoutDetailsService orderCheckoutDetailsService;

    @GetMapping
    public OrderCheckoutDetailsDTO getOrderCheckoutDetailsDTO() {
        return orderCheckoutDetailsService.getOrderCheckoutDetailsDTO();
    }

    @PutMapping
    public OrderCheckoutDetailsDTO updateOrderCheckoutDetails(@RequestBody UpdateOrderCheckoutDetailsDTO updateOrderCheckoutDetailsDTO) {
        return orderCheckoutDetailsService.updateOrderCheckoutDetails(updateOrderCheckoutDetailsDTO);
    }

    @PutMapping("/check-delivery")
    public OrderCheckoutDetailsDTO updateOrderCheckoutDetailsDelivery(@RequestBody UpdateOrderCheckoutDetailsDeliveryDTO updateOrderCheckoutDetailsDeliveryDTO) {
        return orderCheckoutDetailsService.updateOrderCheckoutDetailsDelivery(updateOrderCheckoutDetailsDeliveryDTO);
    }

}
