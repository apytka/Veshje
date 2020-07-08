package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateOrderItemDTO;
import com.agatap.veshje.controller.DTO.OrderItemDTO;
import com.agatap.veshje.service.OrderItemService;
import com.agatap.veshje.service.exception.OrderItemDataInvalidException;
import com.agatap.veshje.service.exception.OrderItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-item")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemDTO> getAllOrderItem() {
        return orderItemService.getAllOrderItem();
    }

    @GetMapping("/{id}")
    public OrderItemDTO findOrderItemDTOById(@PathVariable Integer id) throws OrderItemNotFoundException {
        return orderItemService.findOrderItemDTOById(id);
    }

    @PostMapping
    public OrderItemDTO createOrderItemDTO(@RequestBody CreateUpdateOrderItemDTO createOrderItemDTO) throws OrderItemDataInvalidException {
        return orderItemService.createOrderItemDTO(createOrderItemDTO);
    }

    @PutMapping("/{id}")
    public OrderItemDTO updateOrderItemDTO(@RequestBody CreateUpdateOrderItemDTO updateOrderItemDTO, @PathVariable Integer id)
            throws OrderItemNotFoundException, OrderItemDataInvalidException {
        return orderItemService.updateOrderItemDTO(updateOrderItemDTO, id);
    }

    @DeleteMapping("/{id}")
    public OrderItemDTO deleteOrderItemDTO(@PathVariable Integer id) throws OrderItemNotFoundException {
        return orderItemService.deleteOrderItemDTO(id);
    }

    @GetMapping("/order-item-by-orderId/{orderId}")
    public List<OrderItemDTO> findOrderItemByOrderId(@PathVariable Integer orderId) throws OrderItemNotFoundException {
        return orderItemService.findOrderItemByOrderId(orderId);
    }
}
