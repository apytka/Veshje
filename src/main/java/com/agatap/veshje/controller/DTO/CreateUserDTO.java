package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private Boolean subscribedNewsletter;

    private List<Integer> paymentIds;
    private List<Integer> addressesIds;
    private Integer newsletterId;
    private List<Integer> ordersId;
    private List<Integer> reviewsIds;
}
