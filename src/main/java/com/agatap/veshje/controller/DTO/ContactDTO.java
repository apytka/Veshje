package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.ContactStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDTO {
    private Integer id;
    @NotBlank(message = "This field is required")
    private String firstName;
    @NotBlank(message = "This field is required")
    private String lastName;
    @NotBlank(message = "This field is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    @Pattern(regexp = "^\\+\\d{11}$", message = "Example format phone number: +48000111000")
    @NotBlank(message = "This field is required")
    private String phoneNumber;
    private String orderNumber;
    @NotBlank(message = "This field is required")
    @Length(min = 10, max = 1500, message = "The minimum number of characters is 10 and maximum is 1500")
    private String message;
    private ContactStatus status;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer contactTopicId;
}
