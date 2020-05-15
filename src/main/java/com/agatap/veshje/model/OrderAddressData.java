package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderAddressData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne
    private Orders order;
}
