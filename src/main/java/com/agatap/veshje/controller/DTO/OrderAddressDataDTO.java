package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderAddressDataDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String street;
    private String no;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String information;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer orderId;
}
