package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView displayLoginAndRegister() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("createUser", new CreateUserDTO());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView createUser(@ModelAttribute(name="createUser") CreateUserDTO createUserDTO) throws UserDataInvalidException, UserAlreadyExistException, NewsletterNotFoundException, NewsletterDataInvalidException, NewsletterAlreadyExistsException {
        userService.createUser(createUserDTO);
        return new ModelAndView("redirect:index");
    }
}
