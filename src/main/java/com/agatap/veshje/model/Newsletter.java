package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Newsletter {
    private Integer id;
    private String email;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private User userId;
}
