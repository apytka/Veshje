package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CouponCodeDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCouponCodeDTO;
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
    public List<CouponCodeDTO> getAllCouponsCode() {
        return couponCodeService.getAllCouponsCode();
    }

    @GetMapping("/{id}")
    public CouponCodeDTO findCouponCodeDTOById(@PathVariable Integer id) throws CouponCodeNotFoundException {
        return couponCodeService.findCouponCodeDTOById(id);
    }

    @GetMapping("/code/{code}")
    public CouponCodeDTO findCouponCodeDTOByCode(@PathVariable String code) throws CouponCodeNotFoundException {
        return couponCodeService.findCouponCodeDTOByCode(code);
    }

    @PostMapping
    public CouponCodeDTO createCouponCodeDTO(@RequestBody CreateUpdateCouponCodeDTO createUpdateCouponCodeDTO)
            throws CouponCodeInvalidDataException, CouponCodeAlreadyExistException {
        return couponCodeService.createCouponCodeDTO(createUpdateCouponCodeDTO);
    }

    @PutMapping("/{id}")
    public CouponCodeDTO updateCouponCodeDTO(@PathVariable Integer id, @RequestBody CreateUpdateCouponCodeDTO createUpdateCouponCodeDTO)
            throws CouponCodeInvalidDataException, CouponCodeAlreadyExistException, CouponCodeNotFoundException {
        return couponCodeService.updateCouponCodeDTO(id, createUpdateCouponCodeDTO);
    }

    @DeleteMapping("/{id}")
    public CouponCodeDTO deleteCouponCodeDTO(@PathVariable Integer id) throws CouponCodeNotFoundException {
        return couponCodeService.deleteCouponCodeDTO(id);
    }

    @GetMapping("/check-exists")
    public boolean checkIfCouponExists(String coupon) {
        return couponCodeService.checkIfCouponExists(coupon);
    }
}
