package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
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

//    private Integer deliveryId;
////    private Integer addressId;
////    private Integer paymentId;
}
