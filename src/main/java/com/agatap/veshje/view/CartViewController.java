package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.Delivery;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.ProductInShoppingCartNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.ShoppingCartDataInvalidException;
import com.agatap.veshje.service.exception.SizeNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CartViewController {

    private ShoppingCartService shoppingCartService;
    private DeliveryService deliveryService;
    private ProductService productService;
    private CategoryService categoryService;

    @GetMapping("/shopping-bag")
    public ModelAndView displayCart() throws ProductNotFoundException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<ShoppingCartDTO> shoppingCart = shoppingCartService.getAllProductsInCart();

        List<CategoryDTO> allCategory = categoryService.getAllCategory();
        Map<String, String> map = new HashMap<>();
        for (CategoryDTO category : allCategory) {
            List<ProductDTO> productDTOList = productService.randomProductsInCategory(1, category.getName());
            for (ProductDTO product : productDTOList) {
                List<ImageDTO> images = productService.findImageByProductId(product.getId());
                String base64Encoded = null;
                for (ImageDTO image : images) {
                    byte[] encodeBase64 = Base64.encodeBase64(image.getData());
                    base64Encoded = new String(encodeBase64, "UTF-8");
                }
                map.put(base64Encoded, category.getName());
            }
        }
        DeliveryDTO minPriceDelivery = deliveryService.findMinPriceDeliveryDTO();
        Double total = shoppingCartService.getTotal();
        List<DeliveryDTO> deliveries = deliveryService.getAllDelivery();

        modelAndView.addObject("deliveries", deliveries);
        modelAndView.addObject("minPriceDelivery", minPriceDelivery);
        modelAndView.addObject("map", map);
        modelAndView.addObject("updateShoppingCart", new ShoppingCart());
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("isEmpty", shoppingCartService.shoppingCartIsEmpty());
        modelAndView.addObject("total", total);
        modelAndView.addObject("shoppingCart", shoppingCart);
        return modelAndView;
    }

    @GetMapping("/shopping-bag/add/{id}")
    public ModelAndView addProductToCart(@PathVariable String id, @ModelAttribute(name = "shoppingCart") CreateUpdateShoppingCartDTO shoppingCart)
            throws ProductNotFoundException, UnsupportedEncodingException, SizeNotFoundException {
        shoppingCartService.addProductToShoppingCart(shoppingCart);
        return new ModelAndView("redirect:/products/dress-details/" + id + "?confirmation");
    }


    @GetMapping("/shopping-bag/delete-product")
    public ModelAndView removeProductInCart(@RequestParam Integer id) throws ProductInShoppingCartNotFoundException {
        shoppingCartService.removeProductWithShoppingCart(id);
        return new ModelAndView("redirect:/shopping-bag");
    }

    @GetMapping("/shopping-bag/update/{id}")
    public ModelAndView updateProductInCart(@ModelAttribute(name = "updateShoppingCart") CreateUpdateShoppingCartDTO shoppingCart,
                                            @PathVariable Integer id) throws ProductInShoppingCartNotFoundException, ShoppingCartDataInvalidException {
        shoppingCartService.updateShoppingCartDTO(shoppingCart, id);
        return new ModelAndView("redirect:/shopping-bag");
    }
}
