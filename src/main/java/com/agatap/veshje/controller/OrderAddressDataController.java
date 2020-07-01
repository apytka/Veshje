package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateOrderAddressDataDTO;
import com.agatap.veshje.controller.DTO.OrderAddressDataDTO;
import com.agatap.veshje.model.OrderAddressData;
import com.agatap.veshje.service.OrderAddressDataService;
import com.agatap.veshje.service.exception.AddressDataInvalidException;
import com.agatap.veshje.service.exception.AddressNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-address-data")
public class OrderAddressDataController {
    @Autowired
    private OrderAddressDataService orderAddressDataService;

    @GetMapping
    public List<OrderAddressDataDTO> getAllOrderAddressData() {
        return orderAddressDataService.getAllOrderAddressData();
    }

    @GetMapping("/{id}")
    public OrderAddressDataDTO findOrderAddressDTOById(@PathVariable Integer id) throws AddressNotFoundException {
        return orderAddressDataService.findOrderAddressDTOById(id);
    }

    @PostMapping
    public OrderAddressDataDTO createOrderAddressDTO(@Valid @RequestBody CreateUpdateOrderAddressDataDTO createUpdateOrderAddressDataDTO)
            throws AddressDataInvalidException {
        return orderAddressDataService.createOrderAddressDTO(createUpdateOrderAddressDataDTO);
    }


    @PutMapping("/{id}")
    public OrderAddressDataDTO updateOrderAddressDTO(@Valid @RequestBody CreateUpdateOrderAddressDataDTO createUpdateOrderAddressDataDTO, @PathVariable Integer id)
            throws AddressNotFoundException, AddressDataInvalidException {
        return orderAddressDataService.updateOrderAddressDTO(createUpdateOrderAddressDataDTO, id);
    }

    @DeleteMapping("/{id}")
    public OrderAddressDataDTO deleteOrderAddressDTO(@PathVariable Integer id) throws AddressNotFoundException {
        return orderAddressDataService.deleteOrderAddressDTO(id);
    }

}
