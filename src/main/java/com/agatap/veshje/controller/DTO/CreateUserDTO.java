package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private List<Integer> paymentIds;
    private List<Integer> addressesIds;
    private Integer newsletterId;
    private List<Integer> ordersId;
}
