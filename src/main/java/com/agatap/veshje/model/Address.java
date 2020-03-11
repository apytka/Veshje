package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    private String street;
    private String no;
    private String postalCode;
    private String phoneNumber;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<User> usersId;
//    private City cityId;
//    private Store storeId;
}
