package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String password;
    @Transient
    private String confirmPassword;
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    private Boolean subscribedNewsletter;
    private UserRole userRole;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payments> userPayments = new ArrayList<>();
    @ManyToMany(mappedBy = "users")
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();
    @ManyToOne
    private Newsletter newsletter;
    @OneToMany(mappedBy = "userOrders")
    @Builder.Default
    private List<Orders> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
