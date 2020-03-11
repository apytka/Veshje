package com.agatap.veshje.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareProductDTO {
    private Integer id;
    private String name;
    private String description;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<Picture> pictureList;  //???
}
