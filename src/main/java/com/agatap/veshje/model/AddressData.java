package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AddressData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String street;
    @NotBlank
    private String no;
    @NotBlank
    private String city;
    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private String postalCode;
    @Pattern(regexp = "\\+\\d{11}")
    @NotBlank
    private String phoneNumber;
    @Max(50)
    private String information;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private User user;
}
