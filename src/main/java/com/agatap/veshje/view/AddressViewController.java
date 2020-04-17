package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.AddressData;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.AddressDataService;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class AddressViewController {

    private final Logger LOG = LoggerFactory.getLogger(AddressViewController.class);

    private AddressDataService addressDataService;
    private UserService userService;
    private NewsletterService newsletterService;

    @GetMapping("/addresses")
    public ModelAndView displayAddresses(Authentication authentication) throws UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account-addresses");
        User user = userService.findUserByEmail(authentication.getName());
        List<AddressDataDTO> addresses = addressDataService.findAddressesByUserId(user.getId());
        modelAndView.addObject("addresses", addresses);
        modelAndView.addObject("addNewsletterAddresses", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/addresses")
    public ModelAndView accountUser(@Valid @ModelAttribute(name = "addNewsletterAddresses") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                    BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account-addresses");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return modelAndView;
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:addresses?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:addresses?success");
    }

    @GetMapping("/addresses/delete-address")
    public ModelAndView deleteAddress(@RequestParam Integer id) throws AddressNotFoundException {
        ModelAndView modelAndView = new ModelAndView("redirect:/addresses");
        addressDataService.deleteAddressDTO(id);
        return modelAndView;
    }

    @GetMapping("/modify-address/{id}")
    public ModelAndView displayModelUpdateUser(@PathVariable Integer id) throws AddressNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account-modify-address");

        AddressDataDTO updateAddress = addressDataService.findAddressDataDTOById(id);
        modelAndView.addObject("addNewsletterModifyAddress", new CreateUpdateNewsletterDTO());
        modelAndView.addObject("updateAddress", updateAddress);

        return modelAndView;
    }

    @PostMapping("/modify-address/{id}")
    public ModelAndView updateUser(@PathVariable Integer id, @Valid @ModelAttribute(name = "updateAddress") CreateUpdateAddressDataDTO createUpdateAddressDataDTO,
                                   @ModelAttribute(name = "addNewsletterModifyAddress") CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult) throws UserNotFoundException, AddressNotFoundException, AddressDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account-modify-address");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save new address");
            return modelAndView;
        }
        addressDataService.updateAddressDTO(createUpdateAddressDataDTO, id);
        return new ModelAndView("redirect:/addresses?success");
    }

    @GetMapping("/add-address")
    public ModelAndView displayAddAddress() {
        ModelAndView modelAndView = new ModelAndView("account-add-address");
        modelAndView.addObject("createAddress", new CreateUpdateAddressDataDTO());
        modelAndView.addObject("addNewsletterAddAddress", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/add-address")
    public ModelAndView createNewAddress(@Valid @ModelAttribute(name = "createAddress") CreateUpdateAddressDataDTO createUpdateAddressDataDTO,
                                         BindingResult bindingResult, Authentication authentication) throws AddressDataInvalidException, UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account-add-address");

        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the add user address");
            return new ModelAndView("redirect:add-address?error");
        }
        User user = userService.findUserByEmail(authentication.getName());
        addressDataService.createAddressFromUser(createUpdateAddressDataDTO, user.getId());
        return new ModelAndView("redirect:addresses?success");
    }

    @PostMapping("/add-address-newsletter")
    public ModelAndView addAddressNewsletter(@Valid @ModelAttribute(name = "addNewsletterAddAddress")
                                               CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult) throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account-add-address");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:add-address?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:add-address?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:add-address?success");
    }

}
