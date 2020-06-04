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
public class ShoppingCartDTO {
    private Integer id;
    private String productId;
    private String productName;
    private String productColor;
    private Double productPrice;
    private Double productSalePrice;
    private String productImage;
    private SizeType sizeType;
    private Integer quantity;
    private Integer quantityInStock;
    private String couponCode;
}
