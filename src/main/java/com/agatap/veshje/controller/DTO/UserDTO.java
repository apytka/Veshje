package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

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
}
