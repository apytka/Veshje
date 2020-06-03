package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CouponsCodeDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCouponsCodeDTO;
import com.agatap.veshje.model.CouponCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CouponCodeDTOMapper {

    public CouponsCodeDTO mappingToDTO(CouponCode couponCode) {
        List<Integer> ordersId = couponCode.getOrders().stream()
                .map(orders -> orders.getId())
                .collect(Collectors.toList());
        return CouponsCodeDTO.builder()
                .id(couponCode.getId())
                .code(couponCode.getCode())
                .percentDiscount(couponCode.getPercentDiscount())
                .description(couponCode.getDescription())
                .startDiscount(couponCode.getStartDiscount())
                .expireDiscount(couponCode.getExpireDiscount())
                .ordersId(ordersId)
                .createDate(couponCode.getCreateDate())
                .updateDate(couponCode.getUpdateDate())
                .build();
    }

    public CouponCode mappingToModel(CreateUpdateCouponsCodeDTO createUpdateCouponCodeDTO) {
        return CouponCode.builder()
                .code(createUpdateCouponCodeDTO.getCode())
                .percentDiscount(createUpdateCouponCodeDTO.getPercentDiscount())
                .description(createUpdateCouponCodeDTO.getDescription())
                .startDiscount(createUpdateCouponCodeDTO.getStartDiscount())
                .expireDiscount(createUpdateCouponCodeDTO.getExpireDiscount())
                .build();
    }
}
