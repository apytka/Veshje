package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsTypeDTO;
import com.agatap.veshje.controller.DTO.PaymentsTypeDTO;
import com.agatap.veshje.service.PaymentsTypeService;
import com.agatap.veshje.service.exception.PaymentsTypeAlreadyExistsException;
import com.agatap.veshje.service.exception.PaymentsTypeDataInvalidException;
import com.agatap.veshje.service.exception.PaymentsTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paymentsType")
public class PaymentsTypeController {
    @Autowired
    private PaymentsTypeService paymentsTypeService;

    @GetMapping
    public List<PaymentsTypeDTO> getAllPaymentsTypes() {
        return paymentsTypeService.getAllPaymentsTypes();
    }

    @GetMapping("/{id}")
    public PaymentsTypeDTO findPaymentsTypeDTOById(@PathVariable Integer id) throws PaymentsTypeNotFoundException {
        return paymentsTypeService.findPaymentsTypeDTOById(id);
    }

    @PostMapping
    public PaymentsTypeDTO createPaymentsTypeDTO(@RequestBody CreateUpdatePaymentsTypeDTO createPaymentsTypeDTO)
            throws PaymentsTypeDataInvalidException, PaymentsTypeAlreadyExistsException {
        return paymentsTypeService.createPaymentsTypeDTO(createPaymentsTypeDTO);
    }

    @PutMapping("/{id}")
    public PaymentsTypeDTO updatePaymentsTypeDTO(@RequestBody CreateUpdatePaymentsTypeDTO updatePaymentsTypeDTO, @PathVariable Integer id)
            throws PaymentsTypeNotFoundException {
        return paymentsTypeService.updatePaymentsTypeDTO(updatePaymentsTypeDTO, id);
    }

    @DeleteMapping("/{id}")
    public PaymentsTypeDTO deletePaymentsTypeDTO(@PathVariable Integer id) throws PaymentsTypeNotFoundException {
        return paymentsTypeService.deletePaymentsTypeDTO(id);
    }
}
