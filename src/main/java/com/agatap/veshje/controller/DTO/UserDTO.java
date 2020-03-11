package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.UserRole;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String login;
//    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole userRole;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> paymentIds;
    private List<Integer> addressesIds;
}
