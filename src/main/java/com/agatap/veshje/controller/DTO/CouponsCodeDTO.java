package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponsCodeDTO {
    private Integer id;
    private String code;
    private Double percentDiscount;
    private String description;
    private OffsetDateTime startDiscount;
    private OffsetDateTime expireDiscount;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    List<Integer> ordersId;
}
