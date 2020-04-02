package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private NewsletterService newsletterService;

    @PostMapping("/register")
    public ModelAndView createUser(@Valid @ModelAttribute(name="createUser") CreateUserDTO createUserDTO, BindingResult bindingResult)
            throws UserDataInvalidException, UserAlreadyExistException, AddressDataInvalidException {
        if(bindingResult.hasErrors()) {
            return new ModelAndView();
        }
        userService.createUser(createUserDTO);
        return new ModelAndView("redirect:index");
    }

    @GetMapping("/account")
    public ModelAndView displayAccountUser(@ModelAttribute(name = "addNewsletterAccount")
            CreateUpdateNewsletterDTO createUpdateNewsletterDTO, Authentication authentication)
            throws UserNotFoundException {
        User user = userService.findUserByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("user", user);
        modelAndView.addObject("createAccountDto", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/account")
    public ModelAndView addNewsletterInAccount(
            @Valid @ModelAttribute(name = "addNewsletterAccount") CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        if(bindingResult.hasErrors()) {
            return new ModelAndView("account");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:account");
    }
}
