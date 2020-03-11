package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String street;
    private String no;
    private String postalCode;
    private String phoneNumber;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private City city;
    @OneToOne
    private Store store;
    @ManyToMany
    @Builder.Default
    private List<User> users = new ArrayList<>();
//    private Store storeId;
}
