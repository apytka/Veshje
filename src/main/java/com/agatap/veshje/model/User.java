package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

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
    private String login;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole userRole;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<Address> addressId;
//    private Newsletter newsletterId;
//    private List<Payment> paymentsId;
//    private List<Review> reviewList;
}
