package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    @NotBlank(message = "This field is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    private UserRole userRole;
    private Boolean subscribedNewsletter;
    private boolean enabled;

    private List<Integer> paymentIds;
    private List<Integer> addressesIds;
    private Integer newsletterId;
    private List<Integer> ordersId;
    private List<Integer> reviewsIds;
}
