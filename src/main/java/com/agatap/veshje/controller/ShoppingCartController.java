package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.ChangeCouponCodeDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateShoppingCartDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.service.ShoppingCartService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public List<ShoppingCartDTO> getAllProductsInCart() {
        return shoppingCartService.getAllProductsInCart();
    }

    @PostMapping
    public ShoppingCartDTO addProductToShoppingCart(@RequestBody CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO)
            throws ProductNotFoundException, UnsupportedEncodingException, SizeNotFoundException, CouponCodeNotFoundException {
        return shoppingCartService.addProductToShoppingCart(createUpdateShoppingCartDTO);
    }

    @GetMapping("/products/{id}")
    public List<ShoppingCartDTO> findProductInShoppingCart(@PathVariable String id) {
        return shoppingCartService.findProductInShoppingCart(id);
    }

    @GetMapping("/{id}")
    public ShoppingCartDTO findShoppingCartDTOById(@PathVariable Integer id) throws ProductInShoppingCartNotFoundException {
        return shoppingCartService.findShoppingCartDTOById(id);
    }

    @PutMapping("/{id}")
    public ShoppingCartDTO updateShoppingCartDTO(@RequestBody CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO, @PathVariable Integer id)
            throws ProductInShoppingCartNotFoundException, ShoppingCartDataInvalidException {
        return shoppingCartService.updateShoppingCartDTO(createUpdateShoppingCartDTO, id);
    }

    @DeleteMapping("/{id}")
    public ShoppingCartDTO removeProductWithShoppingCart(@PathVariable Integer id) throws ProductInShoppingCartNotFoundException {
        return shoppingCartService.removeProductWithShoppingCart(id);
    }

    @GetMapping("/filter/{id}/{sizeType}")
    public ShoppingCartDTO filterByProductIdAndSizeTypeDTO (@PathVariable String id, @PathVariable SizeType sizeType) {
        return shoppingCartService.filterByProductIdAndSizeTypeDTO(id, sizeType);
    }

    @PutMapping("/add-code")
    public List<ShoppingCartDTO> addCouponCodeWithShoppingCart(@RequestBody ChangeCouponCodeDTO changeCouponCodeDTO)
            throws CouponCodeInvalidDataException, CouponCodeNotFoundException {
        return shoppingCartService.addCouponCodeToShoppingCart(changeCouponCodeDTO);
    }

    @PutMapping("/remove-code")
    public List<ShoppingCartDTO> removeCouponCodeWithShoppingCart() {
        return shoppingCartService.removeCouponCodeWithShoppingCart();
    }

    @GetMapping("/total-price")
    public Double getTotalPrice() throws ProductNotFoundException {
        return shoppingCartService.getTotalPrice();
    }

    @GetMapping("/total-sale-price")
    public Double getTotalSalePrice() throws ProductNotFoundException {
        return shoppingCartService.getTotalSalePrice();
    }
}
