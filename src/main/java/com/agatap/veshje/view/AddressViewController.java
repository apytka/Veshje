package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.AddressDataDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.model.AddressData;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.AddressDataService;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class AddressViewController {

    private AddressDataService addressDataService;
    private UserService userService;

    @GetMapping("/addresses")
    public ModelAndView displayAddresses(Authentication authentication) throws UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account-addresses");
        User user = userService.findUserByEmail(authentication.getName());
        List<AddressDataDTO> addresses = addressDataService.findAddressesByUserId(user.getId());
        modelAndView.addObject("addresses", addresses);
        modelAndView.addObject("addNewsletterAddresses", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }
}
