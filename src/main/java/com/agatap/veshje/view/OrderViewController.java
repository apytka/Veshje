package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.ChangeCouponCodeDTO;
import com.agatap.veshje.controller.DTO.CouponCodeDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.CouponCodeInvalidDataException;
import com.agatap.veshje.service.exception.CouponCodeNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
public class OrderViewController {

    private UserService userService;
    private AddressDataService addressDataService;
    private PaymentsTypeService paymentsTypeService;
    private ShoppingCartService shoppingCartService;
    private DeliveryService deliveryService;
    private ProductService productService;
    private CouponCodeService couponCodeService;

    @GetMapping("/checkout/order")
    public ModelAndView displayCheckout(Authentication authentication) throws ProductNotFoundException, UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("checkout-order");

        User user = userService.findUserByEmail(authentication.getName());

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

        modelAndView.addObject("shoppingCart", shoppingCartService.getAllProductsInCart());
        modelAndView.addObject("deliveries", deliveryService.getAllDelivery());
        modelAndView.addObject("addresses", addressDataService.findAddressesByUserId(user.getId()));
        modelAndView.addObject("payments", paymentsTypeService.getAllPaymentsTypes());
        modelAndView.addObject("totalDiscount", shoppingCartService.getTotalDiscount());
        modelAndView.addObject("totalPriceWithDelivery", shoppingCartService.getTotalPriceWithDelivery());
        modelAndView.addObject("totalSalePriceWithDelivery", shoppingCartService.getTotalSalePriceWithDelivery());
        modelAndView.addObject("priceDelivery", shoppingCartService.getDeliveryPrice());
        modelAndView.addObject("cartIsEmpty", shoppingCartService.getAllProductsInCart().isEmpty());
        modelAndView.addObject("couponCode", new ChangeCouponCodeDTO());
        modelAndView.addObject("isEmpty", shoppingCartService.shoppingCartIsEmpty());
        modelAndView.addObject("total", shoppingCartService.getTotalPrice());
        modelAndView.addObject("totalSalePrice", shoppingCartService.getTotalSalePrice());
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/checkout/order/add-coupon")
    public ModelAndView addCouponToShoppingBag(@ModelAttribute(name = "couponCode") ChangeCouponCodeDTO changeCouponCodeDTO)
            throws CouponCodeNotFoundException {

        ModelAndView modelAndView = new ModelAndView("checkout-order");
        if (!couponCodeService.checkIfCouponExists(changeCouponCodeDTO.getCouponCode())) {
            modelAndView.addObject("message", "Discount code is not exists or expired");
            return new ModelAndView("redirect:/checkout/order?error");
        }
        try {
            shoppingCartService.addCouponCodeToShoppingCart(changeCouponCodeDTO);
        } catch (CouponCodeInvalidDataException e) {
            return new ModelAndView("redirect:/checkout/order?error");
        }
        return new ModelAndView("redirect:/checkout/order");
    }

    @GetMapping("/checkout/order/remove-coupon")
    public ModelAndView removeCouponCode() {
        shoppingCartService.removeCouponCodeWithShoppingCart();
        return new ModelAndView("redirect:/checkout/order");
    }
}
