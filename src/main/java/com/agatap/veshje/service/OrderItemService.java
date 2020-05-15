package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateOrderItemDTO;
import com.agatap.veshje.controller.DTO.OrderItemDTO;
import com.agatap.veshje.controller.mapper.OrderItemMapper;
import com.agatap.veshje.model.OrderItem;
import com.agatap.veshje.repository.OrderItemRepository;
import com.agatap.veshje.service.exception.OrderItemDataInvalidException;
import com.agatap.veshje.service.exception.OrderItemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private OrderItemMapper mapper;

    public List<OrderItemDTO> getAllOrderItem() {
        return orderItemRepository.findAll().stream()
                .map(orderItem -> mapper.mappingToDTO(orderItem))
                .collect(Collectors.toList());
    }

    public OrderItemDTO findOrderItemDTOById(Integer id) throws OrderItemNotFoundException {
        return orderItemRepository.findById(id)
                .map(orderItem -> mapper.mappingToDTO(orderItem))
                .orElseThrow(() -> new OrderItemNotFoundException());
    }

    public OrderItem findOrderItemById(Integer id) throws OrderItemNotFoundException {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException());
    }

    public OrderItemDTO createOrderItemDTO(CreateUpdateOrderItemDTO createOrderItemDTO) throws OrderItemDataInvalidException {
        if (createOrderItemDTO.getProductPrice() == null || createOrderItemDTO.getProductPrice() <= 0 ||
                createOrderItemDTO.getQuantity() == null || createOrderItemDTO.getQuantity() <= 0) {
            throw new OrderItemDataInvalidException();
        }
        OrderItem orderItem = mapper.mappingToModel(createOrderItemDTO);
        orderItem.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        OrderItem newOrderItem = orderItemRepository.save(orderItem);
        return mapper.mappingToDTO(newOrderItem);
    }

    public OrderItemDTO updateOrderItemDTO(CreateUpdateOrderItemDTO updateOrderItemDTO, Integer id)
            throws OrderItemDataInvalidException, OrderItemNotFoundException {
        if (updateOrderItemDTO.getProductPrice() == null || updateOrderItemDTO.getProductPrice() <= 0 ||
                updateOrderItemDTO.getQuantity() == null || updateOrderItemDTO.getQuantity() <= 0) {
            throw new OrderItemDataInvalidException();
        }
        OrderItem orderItem = findOrderItemById(id);
        orderItem.setProductId(updateOrderItemDTO.getProductId());
        orderItem.setProductName(updateOrderItemDTO.getProductName());
        orderItem.setProductPrice(updateOrderItemDTO.getProductPrice());
        orderItem.setSize(updateOrderItemDTO.getSize());
        orderItem.setQuantity(updateOrderItemDTO.getQuantity());
        orderItem.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        OrderItem updateOrderItem = orderItemRepository.save(orderItem);
        return mapper.mappingToDTO(updateOrderItem);
    }

    public OrderItemDTO deleteOrderItemDTO(Integer id) throws OrderItemNotFoundException {
        OrderItem orderItem = findOrderItemById(id);
        orderItemRepository.delete(orderItem);
        return mapper.mappingToDTO(orderItem);
    }
}
