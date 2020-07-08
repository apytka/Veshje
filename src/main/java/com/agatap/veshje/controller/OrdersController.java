package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateOrdersDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.OrdersService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public List<OrdersDTO> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrdersDTO findOrdersDTOById(@PathVariable Integer id) throws OrdersNotFoundException {
        return ordersService.findOrdersDTOById(id);
    }

    @PostMapping("/{userId}")
    public OrdersDTO createOrdersDTO(@PathVariable Integer userId) throws DeliveryNotFoundException,
            ProductNotFoundException, PaymentsTypeNotFoundException, UserNotFoundException, PaymentsNotFoundException,
            CouponCodeNotFoundException, AddressNotFoundException, PaymentsDataInvalidException, SizeNotFoundException,
            NotEnoughProductsInStockException {
        return ordersService.createOrdersDTO(userId);
    }

    @PutMapping("/{id}")
    public OrdersDTO updateOrdersDTO(@RequestBody CreateUpdateOrdersDTO updateOrdersDTO, @PathVariable Integer id)
            throws OrdersNotFoundException {
        return ordersService.updateOrdersDTO(updateOrdersDTO, id);
    }

    @DeleteMapping("/{id}")
    public OrdersDTO deleteOrdersDTO(@PathVariable Integer id) throws OrdersNotFoundException {
        return ordersService.deleteOrdersDTO(id);
    }

    @GetMapping("/orders-by-user/{id}")
    public List<OrdersDTO> findOrdersByUserId(@PathVariable Integer id) throws UserNotFoundException {
        return ordersService.findOrdersByUserId(id);
    }
}
