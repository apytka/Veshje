package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "^\\+\\d{11}$")
    @NotBlank
    private String phoneNumber;
    private String orderNumber;
    @NotBlank
    private String message;
    private ContactStatus status;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @ManyToOne
    private ContactTopic contactTopic;
}
