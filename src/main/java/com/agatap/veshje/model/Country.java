package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private Integer id;
    private String name;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
//    private List<City> cityList;
}
