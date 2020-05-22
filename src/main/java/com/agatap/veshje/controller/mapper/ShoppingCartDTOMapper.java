package com.agatap.veshje.controller.mapper;

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
                .quantity(shoppingCart.getQuantity())
                .sizeType(shoppingCart.getSizeType())
                .build();
    }

    public ShoppingCart mappingToModel(CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO) {
        return ShoppingCart.builder()
                .productId(createUpdateShoppingCartDTO.getProductId())
                .quantity(createUpdateShoppingCartDTO.getQuantity())
                .sizeType(createUpdateShoppingCartDTO.getSizeType())
                .build();
    }
}
