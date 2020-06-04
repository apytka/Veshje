package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.ChangeCouponCodeDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateShoppingCartDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.model.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartDTOMapper {
    public ShoppingCartDTO mappingToDTO(ShoppingCart shoppingCart) {
        return ShoppingCartDTO.builder()
                .id(shoppingCart.getId())
                .productId(shoppingCart.getProductId())
                .productName(shoppingCart.getProductName())
                .productColor(shoppingCart.getProductColor())
                .productPrice(shoppingCart.getProductPrice())
                .productImage(shoppingCart.getProductImage())
                .quantity(shoppingCart.getQuantity())
                .sizeType(shoppingCart.getSizeType())
                .quantityInStock(shoppingCart.getQuantityInStock())
                .couponCode(shoppingCart.getCouponCode())
                .productSalePrice(shoppingCart.getProductSalePrice())
                .build();
    }

    public ShoppingCart mappingToModel(CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO) {
        return ShoppingCart.builder()
                .productId(createUpdateShoppingCartDTO.getProductId())
                .quantity(createUpdateShoppingCartDTO.getQuantity())
                .sizeType(createUpdateShoppingCartDTO.getSizeType())
                .build();
    }

    public ShoppingCart mapp(ShoppingCartDTO shoppingCart) {
        return ShoppingCart.builder()
                .id(shoppingCart.getId())
                .productId(shoppingCart.getProductId())
                .productName(shoppingCart.getProductName())
                .productColor(shoppingCart.getProductColor())
                .productPrice(shoppingCart.getProductPrice())
                .productImage(shoppingCart.getProductImage())
                .quantity(shoppingCart.getQuantity())
                .sizeType(shoppingCart.getSizeType())
                .quantityInStock(shoppingCart.getQuantityInStock())
                .couponCode(shoppingCart.getCouponCode())
                .build();
    }
}
