package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private Integer id;
    private String token;
    private Date expiryDate;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer userId;
}
