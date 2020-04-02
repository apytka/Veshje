package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Review;
import com.agatap.veshje.model.UserRole;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    @NotBlank(message = "This field is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    private Boolean subscribedNewsletter;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer newsletterId;
    private List<Integer> paymentIds;
    private List<Integer> addressesIds;
    private List<Integer> orderIds;
    private List<Integer> reviewsIds;
}
