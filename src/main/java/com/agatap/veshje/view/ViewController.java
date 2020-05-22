package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
}
