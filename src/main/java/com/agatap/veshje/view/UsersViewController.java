package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    public ModelAndView createUser(@Valid @ModelAttribute(name="createUser") CreateUserDTO createUserDTO, BindingResult bindingResult)
            throws UserDataInvalidException, UserAlreadyExistException, AddressDataInvalidException {
        if(bindingResult.hasErrors()) {
            return new ModelAndView();
        }
        userService.createUser(createUserDTO);
        return new ModelAndView("redirect:index");
    }
}
