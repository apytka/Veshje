package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.Category;
import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private CouponCodeService couponCodeService;
    private SizeService sizeService;

    @GetMapping("/shopping-bag")
    public ModelAndView displayCart() throws ProductNotFoundException, UnsupportedEncodingException, SizeNotFoundException, NotEnoughProductsInStockException, CategoryNotFoundException {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<ShoppingCartDTO> shoppingCart = new ArrayList<>();

        for (ShoppingCartDTO shoppingCartDTO : shoppingCartService.getAllProductsInCart()) {
            shoppingCart.add(shoppingCartDTO);
            Integer quantityBySizeTypeAndProductId = sizeService.getQuantityBySizeTypeAndProductId(shoppingCartDTO.getSizeType(), shoppingCartDTO.getProductId());
            shoppingCartDTO.setQuantityInStock(quantityBySizeTypeAndProductId);
            modelAndView.addObject("shoppingCart", shoppingCart);
            if (!(shoppingCartDTO.getQuantity() > shoppingCartDTO.getQuantityInStock())
                    ||!(shoppingCartDTO.getQuantityInStock() == 0)) {
                modelAndView.addObject("buttonDisabled", true);
            }
        }

        Map<String, String> map = getProductWithImageAfterCategory();
        for (ShoppingCartDTO shoppingCartDTO : shoppingCartService.getAllProductsInCart()) {
            String couponCode = shoppingCartDTO.getCouponCode();
            if (couponCode != null) {
                modelAndView.addObject("addCouponCode", couponCode);
            }
        }

        if (!productService.getAllProducts().isEmpty()) {
            for (ShoppingCartDTO shoppingCart1 : shoppingCartService.getAllProductsInCart()) {
                for (CouponCodeDTO couponCodeDTO : couponCodeService.getAllCouponsCode()) {
                    if (shoppingCart1.getCouponCode() != null && shoppingCart1.getCouponCode().equals(couponCodeDTO.getCode())) {
                        modelAndView.addObject("containsCouponCode", true);
                    }
                }
            }
        }

        modelAndView.addObject("totalDiscount", shoppingCartService.getTotalDiscount());
        modelAndView.addObject("totalPriceWithMinDelivery", shoppingCartService.getTotalPriceWithMinDelivery());
        modelAndView.addObject("totalSalePriceWithMinDelivery", shoppingCartService.getTotalSalePriceWithMinDelivery());
        modelAndView.addObject("priceDelivery", shoppingCartService.getMinDeliveryPrice());
        modelAndView.addObject("deliveries", deliveryService.getAllDelivery());
        modelAndView.addObject("map", map);
        modelAndView.addObject("updateShoppingCart", new ShoppingCart());
        modelAndView.addObject("couponCode", new ChangeCouponCodeDTO());
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("total", shoppingCartService.getTotalPrice());
        modelAndView.addObject("totalSalePrice", shoppingCartService.getTotalSalePrice());
        modelAndView.addObject("isEmpty", shoppingCartService.shoppingCartIsEmpty());
        return modelAndView;
    }

    @GetMapping("/shopping-bag/add/{id}")
    public ModelAndView addProductToCart(@PathVariable String id, @ModelAttribute(name = "shoppingCart") CreateUpdateShoppingCartDTO shoppingCart)
            throws ProductNotFoundException, UnsupportedEncodingException, SizeNotFoundException, CouponCodeNotFoundException {
        try {
            shoppingCartService.checkoutProductStock(shoppingCart);
        } catch (NotEnoughProductsInStockException e) {
            return new ModelAndView("redirect:/products/dress-details/" + id + "?errorStock");
        }
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

    @GetMapping("/shopping-bag/add-coupon")
    public ModelAndView addCouponToShoppingBag(@ModelAttribute(name = "couponCode") ChangeCouponCodeDTO changeCouponCodeDTO)
            throws CouponCodeNotFoundException {

        ModelAndView modelAndView = new ModelAndView("cart");
        if (!couponCodeService.checkIfCouponExists(changeCouponCodeDTO.getCouponCode())) {
            modelAndView.addObject("message", "Discount code is not exists or expired");
            return new ModelAndView("redirect:/shopping-bag?error");
        }
        try {
            shoppingCartService.addCouponCodeToShoppingCart(changeCouponCodeDTO);
        } catch (CouponCodeInvalidDataException e) {
            return new ModelAndView("redirect:/shopping-bag?error");
        }
        return new ModelAndView("redirect:/shopping-bag");
    }

    @GetMapping("/shopping-bag/remove-coupon")
    public ModelAndView removeCouponCode() {
        shoppingCartService.removeCouponCodeWithShoppingCart();
        return new ModelAndView("redirect:/shopping-bag");
    }

    @NotNull
    private Map<String, String> getProductWithImageAfterCategory() throws ProductNotFoundException, UnsupportedEncodingException, CategoryNotFoundException {
        List<CategoryDTO> allCategory = new ArrayList<>();
        allCategory.add(categoryService.findCategoryByName("mini"));
        allCategory.add(categoryService.findCategoryByName("midi"));
        allCategory.add(categoryService.findCategoryByName("long"));
        Map<String, String> map = new HashMap<>();
        for (CategoryDTO category : allCategory) {
            List<ProductDTO> productDTOList = productService.randomProductsInCategory(1, category.getName());
            for (ProductDTO product : productDTOList) {
                List<ImageDTO> images = productService.findImageByProductId(product.getId());
                byte[] encodeBase64 = Base64.encodeBase64(images.get(0).getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                map.put(base64Encoded, category.getName());
            }
        }
        return map;
    }
}
