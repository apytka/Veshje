package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.Token;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.repository.TokenRepository;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.TokenService;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class UsersViewController {

    private final Logger LOG = LoggerFactory.getLogger(UsersViewController.class);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService userService;
    private UserRepository userRepository;
    private NewsletterService newsletterService;
    private TokenService tokenService;
    private TokenRepository tokenRepository;

    @PostMapping("/register")
    public ModelAndView createUser(@Valid @ModelAttribute(name = "createUser")
                                           CreateUserDTO createUserDTO, BindingResult bindingResult)
            throws UserDataInvalidException, UserAlreadyExistException, AddressDataInvalidException, NewsletterNotFoundException {
        ModelAndView modelAndView = new ModelAndView("login");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the registration");
            return modelAndView;
        }
        if (userService.isUserEmailExists(createUserDTO.getEmail())) {
            LOG.warn("User " + createUserDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a user registered with the email provided");
            return modelAndView;
        }
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
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
        modelAndView.addObject("changePassword", new ChangePasswordDTO());
        modelAndView.addObject("updateUserNewsletter", new UpdateUserDTO());
        return modelAndView;
    }


    @PostMapping("/account")
    public ModelAndView accountUser(@Valid @ModelAttribute(name = "addNewsletterAccount") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                    BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:account?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:account?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:account?success");
    }

    @GetMapping("/register")
    public ModelAndView registerVerificationToken(@RequestParam(value = "token", required = false) String token) throws TokenNotFoundException {
        Token byToken = tokenService.findByToken(token);
        User user = byToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        if (user.isEnabled()) {
            tokenService.deleteToken(token);
        }
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
    public ModelAndView updateUser(@Valid @ModelAttribute(name = "updateUser")
                                           UpdateUserDTO updateUserDTO, BindingResult bindingResult, Authentication authentication)
            throws UserNotFoundException, NewsletterNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the update user");
            return new ModelAndView("redirect:account?error");
        }
        User user = userService.findUserByEmail(authentication.getName());
        Integer id = user.getId();

        userService.updateUser(updateUserDTO, id);
        return new ModelAndView("redirect:account?success");
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@Valid @ModelAttribute(name = "changePassword") ChangePasswordDTO changePasswordDTO,
                                       BindingResult bindingResult, Authentication authentication) throws UserNotFoundException, UserDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the change user password");
            return new ModelAndView("redirect:account?error");
        }

        User user = userService.findUserByEmail(authentication.getName());

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            LOG.warn("New password and confirmation password do not match.");
            modelAndView.addObject("message", "New password and confirm password must be the same!");
            return new ModelAndView("redirect:account?error");
        }

        if (passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            userService.updatePassword(user, changePasswordDTO);
            return new ModelAndView("redirect:account?success");
        } else {
            LOG.warn("Current password do not match.");
            modelAndView.addObject("message", "Wrong current password!");
            return new ModelAndView("redirect:account?error");
        }
    }

    @PostMapping("/update-user-newsletter")
    public ModelAndView changeSubscriptionNewsletter(@Valid @ModelAttribute(name = "updateUserNewsletter")
                                                             UserUpdateNewsletterDTO userUpdateNewsletter, Authentication authentication)
            throws UserNotFoundException, NewsletterNotFoundException {

        User user = userService.findUserByEmail(authentication.getName());
        Integer id = user.getId();

        userService.updateSubscriptionNewsletter(userUpdateNewsletter, id);
        return new ModelAndView("redirect:account?success");
    }

    @GetMapping("/account-removal")
    public ModelAndView displayAccountRemoval(Authentication authentication) throws UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account-removal");
        User user = userService.findUserByEmail(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("addNewsletterAccountRemoval", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/account-removal")
    public ModelAndView accountRemoval(@Valid @ModelAttribute(name = "addNewsletterAccountRemoval")
                                               CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult) throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account-removal");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:account-removal?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:account-removal?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:account-removal?success");
    }

    @GetMapping("/login/forgot-password")
    public ModelAndView displayForgotPassword() {
        ModelAndView modelAndView = new ModelAndView("account-forgot-password");
        modelAndView.addObject("addNewsletterAccountRemoval", new CreateUpdateNewsletterDTO());
        modelAndView.addObject("forgotPasswordDTO", new ForgotPasswordDTO());
        return modelAndView;
    }

    @PostMapping("/login/forgot-password")
    public ModelAndView forgotPassword(@Valid @ModelAttribute(name = "forgotPasswordDTO") ForgotPasswordDTO forgotPasswordDTO) throws UserNotFoundException {
        if (!userService.isUserEmailExists(forgotPasswordDTO.getEmail())) {
            return new ModelAndView("redirect:/login/forgot-password?error");
        }
        User user = userService.findUserByEmail(forgotPasswordDTO.getEmail());
        TokenDTO tokenDTO = userService.getTokenByUserId(user.getId());



        userService.sendToken(user, "login/resetPassword?token=",
                "Veshje shop - reset password", "account-reset-password",60);
        return new ModelAndView("redirect:/login/forgot-password?success");
    }
}
