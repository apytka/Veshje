package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private UserRole userRole;
}
