package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateShoppingCartDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.service.ShoppingCartService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            throws ProductInShoppingCartNotFoundException {
        return shoppingCartService.addProductToShoppingCart(createUpdateShoppingCartDTO);
    }

    @GetMapping("/products/{id}")
    public List<ShoppingCartDTO> findProductInShoppingCart(@PathVariable String id) throws ProductInShoppingCartNotFoundException {
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
}
