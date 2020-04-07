package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.VerificationToken;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.repository.VerificationTokenRepository;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
public class UsersViewController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NewsletterService newsletterService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/register")
    public ModelAndView createUser(@Valid @ModelAttribute(name="createUser") CreateUserDTO createUserDTO, BindingResult bindingResult)
            throws UserDataInvalidException, UserAlreadyExistException, AddressDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("login");
        if(bindingResult.hasErrors()) {
            return modelAndView;
        }
        userService.createUser(createUserDTO);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        return new ModelAndView("redirect:account-not-active");
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

    @GetMapping("/register")
    public ModelAndView registerVerificationToken(@RequestParam String token) {
        VerificationToken byToken = verificationTokenRepository.findByToken(token);
        User user = byToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return new ModelAndView("redirect:login");
    }
}
