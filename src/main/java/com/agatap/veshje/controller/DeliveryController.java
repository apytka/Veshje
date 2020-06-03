package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateDeliveryDTO;
import com.agatap.veshje.controller.DTO.DeliveryDTO;
import com.agatap.veshje.service.DeliveryService;
import com.agatap.veshje.service.exception.DeliveryAlreadyExistsException;
import com.agatap.veshje.service.exception.DeliveryDataInvalidException;
import com.agatap.veshje.service.exception.DeliveryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<DeliveryDTO> getAllDelivery() {
        return deliveryService.getAllDelivery();
    }

    @GetMapping("/{id}")
    public DeliveryDTO findDeliveryDTOById(@PathVariable Integer id) throws DeliveryNotFoundException {
        return deliveryService.findDeliveryDTOById(id);
    }

    @PostMapping
    public DeliveryDTO createDeliveryDTO(@RequestBody CreateUpdateDeliveryDTO createDeliveryDTO)
            throws DeliveryDataInvalidException, DeliveryAlreadyExistsException {
        return deliveryService.createDeliveryDTO(createDeliveryDTO);
    }

    @PutMapping("/{id}")
    public DeliveryDTO updateDeliveryDTO(@RequestBody CreateUpdateDeliveryDTO updateDeliveryDTO, @PathVariable Integer id)
            throws DeliveryNotFoundException {
        return deliveryService.updateDeliveryDTO(updateDeliveryDTO, id);
    }

    @DeleteMapping("/{id}")
    public DeliveryDTO deleteDeliveryDTO(@PathVariable Integer id) throws DeliveryNotFoundException {
        return deliveryService.deleteDeliveryDTO(id);
    }

    @GetMapping("/min")
    public DeliveryDTO findMinPriceDeliveryDTO() {
        return deliveryService.findMinPriceDeliveryDTO();
    }
}
