package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateContactDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.service.ContactService;
import com.agatap.veshje.service.ContactTopicService;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.ShoppingCartService;
import com.agatap.veshje.service.exception.ContactDataInvalidException;
import com.agatap.veshje.service.exception.ContactTopicNotFoundException;
import com.agatap.veshje.service.exception.NewsletterAlreadyExistsException;
import com.agatap.veshje.service.exception.NewsletterDataInvalidException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ContactViewController {

    static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    private ShoppingCartService shoppingCartService;
    private ContactTopicService contactTopicService;
    private ContactService contactService;
    private NewsletterService newsletterService;

    @GetMapping("/contact")
    public ModelAndView displayContactPage() {
        ModelAndView modelAndView = new ModelAndView("help-contact");
        modelAndView.addObject("createUpdateContact", new CreateUpdateContactDTO());
        modelAndView.addObject("topicsMessage", contactTopicService.getAllContactTopic());
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("contactAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/contact")
    public ModelAndView sendMessage(@Valid @ModelAttribute(name = "createUpdateContact") CreateUpdateContactDTO createUpdateContactDTO, BindingResult bindingResult) throws ContactDataInvalidException, ContactTopicNotFoundException {
        ModelAndView modelAndView = new ModelAndView("help-contact");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the contact message");
            return modelAndView;
        }

        contactService.createContactDTO(createUpdateContactDTO);
        return new ModelAndView("redirect:/contact?success");
    }

    @PostMapping("/contact/newsletter")
    public ModelAndView termsAndConditionalAddNewsletter(@Valid @ModelAttribute(name = "contactAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                         BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("help-contact");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return modelAndView;
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:/contact?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:/contact?success");
    }


}
