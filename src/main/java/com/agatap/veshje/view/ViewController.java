package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping({"", "/", "/index"})
    public ModelAndView displayMainSite() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("addNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView displayLoginAndRegister(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        if(error != null) {
            LOG.warn("Invalid username or password is incorrect or your account is not activeted");
            modelAndView.addObject("message", "Invalid username or password is incorrect or your account is not activeted.");
        }

        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("createUser", new CreateUserDTO());
        return modelAndView;
    }

    @GetMapping("/confirm-registration")
    public ModelAndView displayConfirmRegistration() {
        return new ModelAndView("confirm-registration");
    }

    @GetMapping("/account-not-active")
    public ModelAndView displayInformationAboutAccountActivation() {
        ModelAndView modelAndView = new ModelAndView("account-not-active");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/shop-at-veshje")
    public ModelAndView displayShopAtVeshjePage() {
        ModelAndView modelAndView = new ModelAndView("help-shop-at-veshje");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/about-product")
    public ModelAndView displayAboutProductPage() {
        ModelAndView modelAndView = new ModelAndView("help-about-product");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/gift-cards")
    public ModelAndView displayGiftsCardsPage() {
        ModelAndView modelAndView = new ModelAndView("help-gift-cards");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/payment")
    public ModelAndView displayPaymentPage() {
        ModelAndView modelAndView = new ModelAndView("help-payment");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/shipping")
    public ModelAndView displayShippingPage() {
        ModelAndView modelAndView = new ModelAndView("help-shipping");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/exchange-and-returns")
    public ModelAndView displayExchangeAndReturnsPage() {
        ModelAndView modelAndView = new ModelAndView("help-exchange-and-returns");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/shops-and-company")
    public ModelAndView displayShopsAndCompanyPage() {
        ModelAndView modelAndView = new ModelAndView("help-shops-and-company");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView displayAboutPage() {
        ModelAndView modelAndView = new ModelAndView("help-about");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/career")
    public ModelAndView displayCareerPage() {
        ModelAndView modelAndView = new ModelAndView("help-career");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/privacy-policy")
    public ModelAndView displayPrivacyPolicyPage() {
        ModelAndView modelAndView = new ModelAndView("help-privacy-policy");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/terms-and-conditional")
    public ModelAndView displayTermsAndConditionalPage() {
        ModelAndView modelAndView = new ModelAndView("help-terms-and-conditional");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

}
