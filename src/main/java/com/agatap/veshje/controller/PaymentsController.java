package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsDTO;
import com.agatap.veshje.controller.DTO.PaymentsDTO;
import com.agatap.veshje.service.PaymentsService;
import com.agatap.veshje.service.exception.PaymentsDataInvalidException;
import com.agatap.veshje.service.exception.PaymentsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {
    @Autowired
    private PaymentsService paymentsService;

    @GetMapping
    public List<PaymentsDTO> getAllPayments() {
        return paymentsService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentsDTO findPaymentsDTOById(@PathVariable Integer id) throws PaymentsNotFoundException {
        return paymentsService.findPaymentsDTOById(id);
    }

    @PostMapping
    public PaymentsDTO createPaymentsDTO(@RequestBody CreateUpdatePaymentsDTO createPaymentsDTO) throws PaymentsDataInvalidException {
        return paymentsService.createPaymentsDTO(createPaymentsDTO);
    }

    @PutMapping("/{id}")
    public PaymentsDTO updatePaymentsDTO(@RequestBody CreateUpdatePaymentsDTO updatePaymentsDTO, @PathVariable Integer id)
            throws PaymentsNotFoundException {
        return paymentsService.updatePaymentsDTO(updatePaymentsDTO, id);
    }

    @DeleteMapping("/{id}")
    public PaymentsDTO deletePaymentsDTO(@PathVariable Integer id) throws PaymentsNotFoundException {
        return paymentsService.deletePaymentsDTO(id);
    }
}
