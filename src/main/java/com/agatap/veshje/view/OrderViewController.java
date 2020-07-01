package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@AllArgsConstructor
@Controller
public class OrderViewController {

    private final Logger LOG = LoggerFactory.getLogger(OrderViewController.class);

    private UserService userService;
    private AddressDataService addressDataService;
    private PaymentsTypeService paymentsTypeService;
    private ShoppingCartService shoppingCartService;
    private DeliveryService deliveryService;
    private ProductService productService;
    private CouponCodeService couponCodeService;
    private OrderCheckoutDetailsService orderCheckoutDetailsService;
    private OrdersService ordersService;

    @GetMapping("/checkout/order")
    public ModelAndView displayCheckout(Authentication authentication) throws ProductNotFoundException, UserNotFoundException, DeliveryNotFoundException {
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

        if(orderCheckoutDetailsService.getOrderCheckoutDetails().getDeliveryId() != null) {
            Double priceDeliveryById = shoppingCartService.checkDeliveryPrice(orderCheckoutDetailsService.getOrderCheckoutDetails().getDeliveryId());
            modelAndView.addObject("priceDeliveryById", priceDeliveryById);
            modelAndView.addObject("totalPriceWithDelivery", shoppingCartService.getTotalPriceWithDelivery(orderCheckoutDetailsService.getOrderCheckoutDetails().getDeliveryId()));
            modelAndView.addObject("totalSalePriceWithDelivery", shoppingCartService.getTotalSalePriceWithDelivery(orderCheckoutDetailsService.getOrderCheckoutDetails().getDeliveryId()));
        }



        modelAndView.addObject("deliveryIdOrderCheckoutDetails", orderCheckoutDetailsService.getOrderCheckoutDetails().getDeliveryId());
        modelAndView.addObject("shoppingCart", shoppingCartService.getAllProductsInCart());
        modelAndView.addObject("deliveries", deliveryService.getAllDelivery());
        modelAndView.addObject("addresses", addressDataService.findAddressesByUserId(user.getId()));
        modelAndView.addObject("payments", paymentsTypeService.getAllPaymentsTypes());
        modelAndView.addObject("totalDiscount", shoppingCartService.getTotalDiscount());
        modelAndView.addObject("priceDelivery", shoppingCartService.getMinDeliveryPrice());
        modelAndView.addObject("cartIsEmpty", shoppingCartService.getAllProductsInCart().isEmpty());
        modelAndView.addObject("couponCode", new ChangeCouponCodeDTO());
        modelAndView.addObject("isEmpty", shoppingCartService.shoppingCartIsEmpty());
        modelAndView.addObject("total", shoppingCartService.getTotalPrice());
        modelAndView.addObject("totalSalePrice", shoppingCartService.getTotalSalePrice());
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("createAddress", new CreateUpdateAddressDataDTO());
        modelAndView.addObject("orderCheckoutDetailsDelivery", new UpdateOrderCheckoutDetailsDeliveryDTO());
        modelAndView.addObject("orderCheckoutDetails", new UpdateOrderCheckoutDetailsDTO());
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

    @PostMapping("/checkout/order/add-address")
    public ModelAndView addAddressInOrder(@Valid @ModelAttribute(name = "createAddress") CreateUpdateAddressDataDTO createAddressDataDTO,
                                          BindingResult bindingResult, Authentication authentication)
            throws AddressDataInvalidException, UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("checkout-order");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the add user address");
            return new ModelAndView("redirect:/checkout/order?error");
        }
        User user = userService.findUserByEmail(authentication.getName());
        addressDataService.createAddressFromUser(createAddressDataDTO, user.getId());
        return new ModelAndView("redirect:/checkout/order?success");
    }

    @PostMapping("/checkout/order/deliveries")
    public ModelAndView updateProductInCart(@ModelAttribute(name = "orderCheckoutDetailsDelivery")
                                                    UpdateOrderCheckoutDetailsDeliveryDTO updateOrderCheckoutDetailsDeliveryDTO) {
        orderCheckoutDetailsService.updateOrderCheckoutDetailsDelivery(updateOrderCheckoutDetailsDeliveryDTO);
        return new ModelAndView("redirect:/checkout/order");
    }

    @PostMapping("/checkout/order/placeOrder")
    public ModelAndView placeOrder(@ModelAttribute(name = "orderCheckoutDetails") UpdateOrderCheckoutDetailsDTO updateOrderCheckoutDetailsDTO,
                                   Authentication authentication)
            throws DeliveryNotFoundException, ProductNotFoundException, PaymentsTypeNotFoundException, UserNotFoundException, PaymentsNotFoundException, CouponCodeNotFoundException, AddressNotFoundException, PaymentsDataInvalidException {
        orderCheckoutDetailsService.updateOrderCheckoutDetails(updateOrderCheckoutDetailsDTO);
        User user = userService.findUserByEmail(authentication.getName());
        ordersService.createOrdersDTO(user.getId());
        return new ModelAndView("redirect:?success");
    }
}
