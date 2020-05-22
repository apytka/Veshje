package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateShoppingCartDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.service.ShoppingCartService;
import com.agatap.veshje.service.exception.ProductInShoppingCartNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartViewController {

    private ShoppingCartService shoppingCartService;

    @GetMapping("/shopping-bag")
    public ModelAndView displayCart() throws ProductNotFoundException {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<ShoppingCartDTO> shoppingCart = shoppingCartService.getAllProductsInCart();
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("isEmpty", shoppingCartService.shoppingCartIsEmpty());
        modelAndView.addObject("total", shoppingCartService.getTotal());
        modelAndView.addObject("shoppingCart", shoppingCart);
        return modelAndView;
    }

    @GetMapping("/shopping-bag/add")
    public ModelAndView addProductToCart(@ModelAttribute(name = "shoppingCart") CreateUpdateShoppingCartDTO shoppingCart)
            throws ProductInShoppingCartNotFoundException {
        shoppingCartService.addProductToShoppingCart(shoppingCart);
        return new ModelAndView("redirect:/shopping-bag");
    }
}
