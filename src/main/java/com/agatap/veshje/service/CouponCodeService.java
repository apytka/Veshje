package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CouponCodeDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCouponCodeDTO;
import com.agatap.veshje.controller.mapper.CouponCodeDTOMapper;
import com.agatap.veshje.model.CouponCode;
import com.agatap.veshje.repository.CouponCodeRepository;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CouponCodeService {

    private CouponCodeRepository couponCodeRepository;
    private CouponCodeDTOMapper mapper;

    public List<CouponCodeDTO> getAllCouponsCode() {
        return couponCodeRepository.findAll().stream()
                .map(couponsCode -> mapper.mappingToDTO(couponsCode))
                .collect(Collectors.toList());
    }

    public CouponCodeDTO findCouponCodeDTOById(Integer id) throws CouponCodeNotFoundException {
        return couponCodeRepository.findById(id)
                .map(couponsCode -> mapper.mappingToDTO(couponsCode))
                .orElseThrow(() -> new CouponCodeNotFoundException());
    }

    public CouponCode findCouponCodeById(Integer id) throws CouponCodeNotFoundException {
        return couponCodeRepository.findById(id)
                .orElseThrow(() -> new CouponCodeNotFoundException());
    }

    public CouponCodeDTO createCouponCodeDTO(CreateUpdateCouponCodeDTO createUpdateCouponCodeDTO) throws CouponCodeAlreadyExistException, CouponCodeInvalidDataException {
        invalidCouponCodeData(createUpdateCouponCodeDTO);
        CouponCode couponCode = mapper.mappingToModel(createUpdateCouponCodeDTO);
        couponCode.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        CouponCode newCouponCode = couponCodeRepository.save(couponCode);
        return mapper.mappingToDTO(newCouponCode);
    }

    public CouponCodeDTO updateCouponCodeDTO(Integer id, CreateUpdateCouponCodeDTO createUpdateCouponCodeDTO)
            throws CouponCodeInvalidDataException, CouponCodeAlreadyExistException, CouponCodeNotFoundException {
        invalidCouponCodeData(createUpdateCouponCodeDTO);
        CouponCode couponCodeById = findCouponCodeById(id);
        couponCodeById.setCode(createUpdateCouponCodeDTO.getCode().toLowerCase());
        couponCodeById.setDescription(createUpdateCouponCodeDTO.getDescription());
        couponCodeById.setPercentDiscount(createUpdateCouponCodeDTO.getPercentDiscount());
        couponCodeById.setStartDiscount(createUpdateCouponCodeDTO.getStartDiscount());
        couponCodeById.setExpireDiscount(createUpdateCouponCodeDTO.getExpireDiscount());
        couponCodeById.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        CouponCode newCouponCode = couponCodeRepository.save(couponCodeById);
        return mapper.mappingToDTO(newCouponCode);
    }

    @Transactional
    public CouponCodeDTO deleteCouponCodeDTO(Integer id) throws CouponCodeNotFoundException {
        CouponCode couponCodeById = findCouponCodeById(id);
        couponCodeRepository.delete(couponCodeById);
        return mapper.mappingToDTO(couponCodeById);
    }

    private void invalidCouponCodeData(CreateUpdateCouponCodeDTO createUpdateCouponCodeDTO) throws CouponCodeAlreadyExistException, CouponCodeInvalidDataException {
        if(couponCodeRepository.existsByCode(createUpdateCouponCodeDTO.getCode().toLowerCase())) {
            throw new CouponCodeAlreadyExistException();
        }

        if(createUpdateCouponCodeDTO.getPercentDiscount() == null || createUpdateCouponCodeDTO.getPercentDiscount() <= 0
                || createUpdateCouponCodeDTO.getPercentDiscount() >= 81) {
            throw new CouponCodeInvalidDataException();
        }

        if(createUpdateCouponCodeDTO.getStartDiscount().isBefore(OffsetDateTime.now())
                || createUpdateCouponCodeDTO.getExpireDiscount().isBefore(OffsetDateTime.now())
                || createUpdateCouponCodeDTO.getExpireDiscount().isBefore(OffsetDateTime.now().plusDays(1))) {
            throw new CouponCodeInvalidDataException();
        }
    }

    public CouponCodeDTO findCouponCodeDTOByCode(String code) throws CouponCodeNotFoundException {
        return couponCodeRepository.findByCode(code)
                .map(couponCode -> mapper.mappingToDTO(couponCode))
                .orElseThrow(() -> new CouponCodeNotFoundException());
    }

    public boolean checkIfCouponExists(String coupon) {
        return couponCodeRepository.existsByCode(coupon);
    }
}
