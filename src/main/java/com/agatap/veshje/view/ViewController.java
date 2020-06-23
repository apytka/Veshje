package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.service.NewsletterService;
import com.agatap.veshje.service.ShoppingCartService;
import com.agatap.veshje.service.exception.NewsletterAlreadyExistsException;
import com.agatap.veshje.service.exception.NewsletterDataInvalidException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ViewController {

    static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private NewsletterService newsletterService;

    @GetMapping({"", "/", "/index"})
    public ModelAndView displayMainSite() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("addNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView displayLoginAndRegister(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        if(error != null) {
            LOG.warn("Invalid username or password is incorrect or your account is not activeted");
            modelAndView.addObject("message", "Invalid username or password is incorrect or your account is not activeted.");
        }

        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("createUser", new CreateUserDTO());
        return modelAndView;
    }

    @GetMapping("/confirm-registration")
    public ModelAndView displayConfirmRegistration() {
        return new ModelAndView("confirm-registration");
    }

    @GetMapping("/account-not-active")
    public ModelAndView displayInformationAboutAccountActivation() {
        ModelAndView modelAndView = new ModelAndView("account-not-active");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        return modelAndView;
    }

    @GetMapping("/shop-at-veshje")
    public ModelAndView displayShopAtVeshjePage() {
        ModelAndView modelAndView = new ModelAndView("help-shop-at-veshje");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("shopAtVeshjeAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/shop-at-veshje/newsletter")
    public ModelAndView shopAddVeshjeAddNewsletter(@Valid @ModelAttribute(name = "shopAtVeshjeAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                             BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "shop-at-veshje";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/about-product")
    public ModelAndView displayAboutProductPage() {
        ModelAndView modelAndView = new ModelAndView("help-about-product");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("aboutProductAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/about-product/newsletter")
    public ModelAndView aboutProductAddNewsletter(@Valid @ModelAttribute(name = "aboutProductAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "about-product";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);

        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/gift-cards")
    public ModelAndView displayGiftsCardsPage() {
        ModelAndView modelAndView = new ModelAndView("help-gift-cards");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("giftsCardsAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/gift-cards/newsletter")
    public ModelAndView giftsCardsAddNewsletter(@Valid @ModelAttribute(name = "giftsCardsAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "gift-cards";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/payment")
    public ModelAndView displayPaymentPage() {
        ModelAndView modelAndView = new ModelAndView("help-payment");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("paymentAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/payment/newsletter")
    public ModelAndView paymentAddNewsletter(@Valid @ModelAttribute(name = "paymentAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "payment";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/shipping")
    public ModelAndView displayShippingPage() {
        ModelAndView modelAndView = new ModelAndView("help-shipping");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("shippingAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/shipping/newsletter")
    public ModelAndView shippingAddNewsletter(@Valid @ModelAttribute(name = "shippingAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "shipping";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/exchange-and-returns")
    public ModelAndView displayExchangeAndReturnsPage() {
        ModelAndView modelAndView = new ModelAndView("help-exchange-and-returns");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("exchangeAndReturnsCardsAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/exchange-and-returns/newsletter")
    public ModelAndView exchangeAndReturnsAddNewsletter(@Valid @ModelAttribute(name = "exchangeAndReturnsCardsAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "exchange-and-returns";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/about")
    public ModelAndView displayAboutPage() {
        ModelAndView modelAndView = new ModelAndView("help-about");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("aboutAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/about/newsletter")
    public ModelAndView aboutAddNewsletter(@Valid @ModelAttribute(name = "aboutAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "about";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/career")
    public ModelAndView displayCareerPage() {
        ModelAndView modelAndView = new ModelAndView("help-career");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("careerAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/career/newsletter")
    public ModelAndView careerAddNewsletter(@Valid @ModelAttribute(name = "careerAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "career";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/privacy-policy")
    public ModelAndView displayPrivacyPolicyPage() {
        ModelAndView modelAndView = new ModelAndView("help-privacy-policy");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("privacyPolicyAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/privacy-policy/newsletter")
    public ModelAndView privacyPolicyAddNewsletter(@Valid @ModelAttribute(name = "privacyPolicyAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "privacy-policy";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @GetMapping("/terms-and-conditional")
    public ModelAndView displayTermsAndConditionalPage() {
        ModelAndView modelAndView = new ModelAndView("help-terms-and-conditional");
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("termsAndConditionalAddNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/terms-and-conditional/newsletter")
    public ModelAndView termsAndConditionalAddNewsletter(@Valid @ModelAttribute(name = "termsAndConditionalAddNewsletter") CreateUpdateNewsletterDTO createUpdateNewsletterDTO,
                                                   BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        String modelName = "terms-and-conditional";
        ModelAndView modelAndView = new ModelAndView("help-" + modelName);
        return validateAddNewsletter(createUpdateNewsletterDTO, bindingResult, modelAndView, modelName);
    }

    @NotNull
    private ModelAndView validateAddNewsletter(@ModelAttribute(name = "shopAtVeshjeAddNewsletter") @Valid CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult, ModelAndView modelAndView, String modelName) throws NewsletterDataInvalidException, NewsletterAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return modelAndView;
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:/" + modelName +"?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:/" + modelName + "?success");
    }
}
