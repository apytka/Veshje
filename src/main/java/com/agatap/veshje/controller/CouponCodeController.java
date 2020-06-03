package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CouponsCodeDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCouponsCodeDTO;
import com.agatap.veshje.service.CouponCodeService;
import com.agatap.veshje.service.exception.CouponCodeAlreadyExistException;
import com.agatap.veshje.service.exception.CouponCodeInvalidDataException;
import com.agatap.veshje.service.exception.CouponCodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon-code")
public class CouponCodeController {

    @Autowired
    private CouponCodeService couponCodeService;

    @GetMapping
    public List<CouponsCodeDTO> getAllCouponsCode() {
        return couponCodeService.getAllCouponsCode();
    }

    @GetMapping("/{id}")
    public CouponsCodeDTO findCouponCodeDTOById(@PathVariable Integer id) throws CouponCodeNotFoundException {
        return couponCodeService.findCouponCodeDTOById(id);
    }

    @GetMapping("/code/{code}")
    public CouponsCodeDTO findCouponCodeDTOByCode(@PathVariable String code) throws CouponCodeNotFoundException {
        return couponCodeService.findCouponCodeDTOByCode(code);
    }

    @PostMapping
    public CouponsCodeDTO createCouponCodeDTO(@RequestBody CreateUpdateCouponsCodeDTO createUpdateCouponCodeDTO)
            throws CouponCodeInvalidDataException, CouponCodeAlreadyExistException {
        return couponCodeService.createCouponCodeDTO(createUpdateCouponCodeDTO);
    }

    @PutMapping("/{id}")
    public CouponsCodeDTO updateCouponCodeDTO(@PathVariable Integer id, @RequestBody CreateUpdateCouponsCodeDTO createUpdateCouponCodeDTO)
            throws CouponCodeInvalidDataException, CouponCodeAlreadyExistException, CouponCodeNotFoundException {
        return couponCodeService.updateCouponCodeDTO(id, createUpdateCouponCodeDTO);
    }

    @DeleteMapping("/{id}")
    public CouponsCodeDTO deleteCouponCodeDTO(@PathVariable Integer id) throws CouponCodeNotFoundException {
        return couponCodeService.deleteCouponCodeDTO(id);
    }
}
