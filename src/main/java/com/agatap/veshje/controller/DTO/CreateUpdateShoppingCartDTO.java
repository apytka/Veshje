package com.agatap.veshje.controller.DTO;

import com.agatap.veshje.model.SizeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateShoppingCartDTO {
    private String productId;
    private SizeType sizeType;
    private Integer quantity;
    private Integer deliveryId;
    private Integer addressId;
    private Integer paymentId;
}
