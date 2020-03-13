package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsTypeDTO;
import com.agatap.veshje.controller.DTO.PaymentsTypeDTO;
import com.agatap.veshje.controller.mapper.PaymentsTypeDTOMapper;
import com.agatap.veshje.model.PaymentsType;
import com.agatap.veshje.repository.PaymentsTypeRepository;
import com.agatap.veshje.service.exception.PaymentsTypeAlreadyExistsException;
import com.agatap.veshje.service.exception.PaymentsTypeDataInvalidException;
import com.agatap.veshje.service.exception.PaymentsTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentsTypeService {
    @Autowired
    private PaymentsTypeRepository paymentsTypeRepository;
    @Autowired
    private PaymentsTypeDTOMapper mapper;

    public List<PaymentsTypeDTO> getAllPaymentsTypes() {
        return paymentsTypeRepository.findAll().stream()
                .map(paymentsTypes -> mapper.mappingToDTO(paymentsTypes))
                .collect(Collectors.toList());
    }

    public PaymentsTypeDTO findPaymentsTypeDTOById(Integer id) throws PaymentsTypeNotFoundException {
        return paymentsTypeRepository.findById(id)
                .map(paymentsTypes -> mapper.mappingToDTO(paymentsTypes))
                .orElseThrow(() -> new PaymentsTypeNotFoundException());
    }

    public PaymentsType findPaymentsTypeById(Integer id) throws PaymentsTypeNotFoundException {
        return paymentsTypeRepository.findById(id)
                .orElseThrow(() -> new PaymentsTypeNotFoundException());
    }

    public PaymentsTypeDTO createPaymentsTypeDTO(CreateUpdatePaymentsTypeDTO createPaymentsTypeDTO)
            throws PaymentsTypeDataInvalidException, PaymentsTypeAlreadyExistsException {
        if (createPaymentsTypeDTO.getName() == null) {
            throw new PaymentsTypeDataInvalidException();
        }
        if(paymentsTypeRepository.existsByName(createPaymentsTypeDTO.getName())) {
            throw new PaymentsTypeAlreadyExistsException();
        }

        PaymentsType paymentsType = mapper.mappingToModel(createPaymentsTypeDTO);
        paymentsType.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        PaymentsType newPaymentsType = paymentsTypeRepository.save(paymentsType);
        return mapper.mappingToDTO(newPaymentsType);
    }

    public PaymentsTypeDTO updatePaymentsTypeDTO(CreateUpdatePaymentsTypeDTO updatePaymentsTypeDTO, Integer id)
            throws PaymentsTypeNotFoundException {
        PaymentsType paymentsType = findPaymentsTypeById(id);
        paymentsType.setName(updatePaymentsTypeDTO.getName());
        paymentsType.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        PaymentsType updatePaymentsType = paymentsTypeRepository.save(paymentsType);
        return mapper.mappingToDTO(updatePaymentsType);
    }

    public PaymentsTypeDTO deletePaymentsTypeDTO(Integer id) throws PaymentsTypeNotFoundException {
        PaymentsType paymentsType = findPaymentsTypeById(id);
        paymentsTypeRepository.delete(paymentsType);
        return mapper.mappingToDTO(paymentsType);
    }
}

