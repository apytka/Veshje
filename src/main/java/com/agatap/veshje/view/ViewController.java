package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @GetMapping("/confirm-registration")
    public ModelAndView displayConfirmRegistration() {
        return new ModelAndView("confirm-registration");
    }

    @GetMapping({"", "/", "/index"})
    public ModelAndView displayMainSite() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("addNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView displayLoginAndRegister() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("createUser", new CreateUserDTO());
        return modelAndView;
    }




}
