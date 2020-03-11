package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateAddressDTO {
    private String street;
    private String no;
    private String postalCode;
    private String phoneNumber;

    private Integer cityId;
    private Integer storeId;
    private Integer usersId;
}
