package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Integer id;
    private String street;
    private String no;
    private String postalCode;
    private String phoneNumber;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer cityId;
    private Integer storeId;
    private Integer usersId;
}
