package com.agatap.veshje.controller.DTO;

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
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
}
