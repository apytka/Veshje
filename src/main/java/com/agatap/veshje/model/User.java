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
public class User {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<Address> addressId;
//    private Newsletter newsletterId;
//    private List<Payment> paymentsId;
//    private List<Review> reviewList;
}
