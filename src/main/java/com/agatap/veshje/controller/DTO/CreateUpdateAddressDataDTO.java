package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateAddressDataDTO {
    @NotBlank(message = "This field is required")
    private String firstName;
    @NotBlank(message = "This field is required")
    private String lastName;
    @NotBlank(message = "This field is required")
    private String street;
    @NotBlank(message = "This field is required")
    private String no;
    @NotBlank(message = "This field is required")
    private String city;
    @NotBlank(message = "This field is required")
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Example format postal code: 51-250")
    private String postalCode;
    @Pattern(regexp = "^\\+\\d{11}$", message = "Example format phone number: +48000111000")
    @NotBlank(message = "This field is required")
    private String phoneNumber;
    @Length(max = 50, message = "The maximum number of characters is 50")
    private String information;

    private Integer userId;
}
