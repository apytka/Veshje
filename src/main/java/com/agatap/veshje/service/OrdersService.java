package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateOrdersDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.controller.mapper.OrdersDTOMapper;
import com.agatap.veshje.model.Orders;
import com.agatap.veshje.repository.OrdersRepository;
import com.agatap.veshje.service.exception.OrdersDataInvalidException;
import com.agatap.veshje.service.exception.OrdersNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersDTOMapper mapper;

    public List<OrdersDTO> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(orders -> mapper.mappingToDTO(orders))
                .collect(Collectors.toList());
    }

    public OrdersDTO findOrdersDTOById(Integer id) throws OrdersNotFoundException {
        return ordersRepository.findById(id)
                .map(orders -> mapper.mappingToDTO(orders))
                .orElseThrow(() -> new OrdersNotFoundException());
    }

    public Orders findOrdersOById(Integer id) throws OrdersNotFoundException {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new OrdersNotFoundException());
    }

    public OrdersDTO createOrdersDTO(CreateUpdateOrdersDTO createOrdersDTO) throws OrdersDataInvalidException {
        Orders orders = mapper.mappingToModel(createOrdersDTO);
        orders.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Orders newOrders = ordersRepository.save(orders);
        return mapper.mappingToDTO(newOrders);
    }

    public OrdersDTO updateOrdersDTO(CreateUpdateOrdersDTO updateOrdersDTO, Integer id) throws OrdersNotFoundException {
        Orders orders = findOrdersOById(id);
        orders.setOrderStatus(updateOrdersDTO.getOrderStatus());
        orders.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Orders updateOrders = ordersRepository.save(orders);
        return mapper.mappingToDTO(updateOrders);
    }

    public OrdersDTO deleteOrdersDTO(Integer id) throws OrdersNotFoundException {
        Orders orders = findOrdersOById(id);
        ordersRepository.delete(orders);
        return mapper.mappingToDTO(orders);
    }
}
