package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeForgotPasswordDTO {
    @NotBlank(message = "This field is required")
    @Pattern(regexp="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}",
            message = "You password must be at least 5 characters as well as contain at least one uppercase, one lowercase, and one number.")
    private String newPassword;
    @NotBlank(message = "This field is required")
    @Pattern(regexp="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}",
            message = "You password must be at least 5 characters as well as contain at least one uppercase, one lowercase, and one number.")
    private String confirmPassword;
}
