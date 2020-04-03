package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "This field is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "This field is required")
    @Pattern(regexp="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}",
            message = "You password must be at least 5 characters as well as contain at least one uppercase, one lowercase, and one number.")
    private String password;
    @NotBlank(message = "This field is required")
    @Pattern(regexp="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}",
            message = "You password must be at least 5 characters as well as contain at least one uppercase, one lowercase, and one number.")
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private Boolean subscribedNewsletter;
    private boolean enabled;

    private List<Integer> paymentIds;
    private List<Integer> addressesIds;
    private Integer newsletterId;
    private List<Integer> ordersId;
    private List<Integer> reviewsIds;
}
