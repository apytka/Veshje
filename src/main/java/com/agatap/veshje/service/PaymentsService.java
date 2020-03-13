package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsDTO;
import com.agatap.veshje.controller.DTO.PaymentsDTO;
import com.agatap.veshje.controller.mapper.PaymentsDTOMapper;
import com.agatap.veshje.model.Payments;
import com.agatap.veshje.repository.PaymentsRepository;
import com.agatap.veshje.service.exception.PaymentsDataInvalidException;
import com.agatap.veshje.service.exception.PaymentsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentsService {
    @Autowired
    private PaymentsRepository paymentsRepository;
    @Autowired
    private PaymentsDTOMapper mapper;

    public List<PaymentsDTO> getAllPayments() {
        return paymentsRepository.findAll().stream()
                .map(payments -> mapper.mappingToDTO(payments))
                .collect(Collectors.toList());
    }

    public PaymentsDTO findPaymentsDTOById(Integer id) throws PaymentsNotFoundException {
        return paymentsRepository.findById(id)
                .map(payments -> mapper.mappingToDTO(payments))
                .orElseThrow(() -> new PaymentsNotFoundException());
    }

    public Payments findPaymentsById(Integer id) throws PaymentsNotFoundException {
        return paymentsRepository.findById(id)
                .orElseThrow(() -> new PaymentsNotFoundException());
    }

    public PaymentsDTO createPaymentsDTO(CreateUpdatePaymentsDTO createPaymentsDTO) throws PaymentsDataInvalidException {
        if(createPaymentsDTO.getAmount().doubleValue() <= 0) {
            throw new PaymentsDataInvalidException();
        }
        Payments payments = mapper.mappingToModel(createPaymentsDTO);
        payments.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Payments newPayments = paymentsRepository.save(payments);
        return mapper.mappingToDTO(newPayments);
    }

    public PaymentsDTO updatePaymentsDTO(CreateUpdatePaymentsDTO updatePaymentsDTO, Integer id)
            throws PaymentsNotFoundException {
        Payments payments = findPaymentsById(id);
        payments.setAmount(updatePaymentsDTO.getAmount());
        payments.setPaymentStatus(updatePaymentsDTO.getPaymentStatus());
        payments.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Payments updatePayments = paymentsRepository.save(payments);
        return mapper.mappingToDTO(updatePayments);
    }

    public PaymentsDTO deletePaymentsDTO(Integer id) throws PaymentsNotFoundException {
        Payments payments = findPaymentsById(id);
        paymentsRepository.delete(payments);
        return mapper.mappingToDTO(payments);
    }
}

