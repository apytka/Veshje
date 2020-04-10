package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.controller.DTO.UpdateUserDTO;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.VerificationToken;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.repository.VerificationTokenRepository;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class UsersViewController {

    private final Logger LOG = LoggerFactory.getLogger(UsersViewController.class);

    private UserService userService;
    private UserRepository userRepository;
    private NewsletterService newsletterService;
    private VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/register")
    public ModelAndView createUser(@Valid @ModelAttribute(name="createUser")
                                   CreateUserDTO createUserDTO, BindingResult bindingResult)
            throws UserDataInvalidException, UserAlreadyExistException, AddressDataInvalidException, NewsletterAlreadyExistsException, NewsletterNotFoundException {
        ModelAndView modelAndView = new ModelAndView("login");
        if(bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the registration");
            return modelAndView;
        }
        if(userService.isUserEmailExists(createUserDTO.getEmail())) {
            LOG.warn("User " + createUserDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a user registered with the email provided");
            return modelAndView;
        }
        if(!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            LOG.warn("Error! Passwords do not match");
            modelAndView.addObject("message", "Incorrectly entered data, passwords do not match");
            return modelAndView;
        }

        userService.createUser(createUserDTO);
        return new ModelAndView("redirect:account-not-active");
    }

    @GetMapping("/account")
    public ModelAndView displayAccountUser(Authentication authentication)
            throws UserNotFoundException {
        User user = userService.findUserByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("updateUser", user);
        modelAndView.addObject("addNewsletterAccount", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }


    @PostMapping("/account")
    public ModelAndView addNewsletterInAccount(@Valid @ModelAttribute(name = "addNewsletterAccount") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                               BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account");
        if(bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return modelAndView;
        }
        if(newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return modelAndView;
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerVerificationToken(@RequestParam(value = "token", required = false) String token) {
        VerificationToken byToken = verificationTokenRepository.findByToken(token);
        User user = byToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return new ModelAndView("redirect:login");
    }

    @GetMapping("/delete-user")
    public ModelAndView deleteUser(Authentication authentication, HttpSession session) throws UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("redirect:index");
        User user = userService.findUserByEmail(authentication.getName());
        Integer id = user.getId();
        userService.deleteUser(id);
        session.invalidate();
        return modelAndView;
    }



    @PostMapping("/update-user")
    public ModelAndView updateUser(@Valid @ModelAttribute(name="updateUser")
                                               UpdateUserDTO updateUserDTO, BindingResult bindingResult, Authentication authentication)
            throws UserNotFoundException, NewsletterNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account");
        User user = userService.findUserByEmail(authentication.getName());
        Integer id = user.getId();
        userService.updateUser(updateUserDTO, id);
        return modelAndView;
    }
}
