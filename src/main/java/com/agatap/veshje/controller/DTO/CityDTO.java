package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Address;
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
public class CityDTO {
    private String name;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer countryIds;
    private List<Integer> addressIds;
}
