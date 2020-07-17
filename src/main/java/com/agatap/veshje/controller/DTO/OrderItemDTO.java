package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.SizeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer id;
    private String productId;
    private String productName;
    private Double productPrice;
    private SizeType sizeType;
    private Integer quantity;
    private boolean addReview;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    private Integer orderId;
}
