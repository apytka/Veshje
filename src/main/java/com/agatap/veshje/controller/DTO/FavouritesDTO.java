package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Product;
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
public class FavouritesDTO {
    private Integer id;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private List<Integer> productsId;
    private Integer userId;
}
