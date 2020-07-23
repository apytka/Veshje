package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateNewsletterDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.utils.Utils;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class InvoiceViewController {

    private final Logger LOG = LoggerFactory.getLogger(AddressViewController.class);
    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private OrdersService ordersService;
    private NewsletterService newsletterService;
    private InvoiceService invoiceService;

    @GetMapping("/invoices")
    public ModelAndView displayInvoices(Authentication authentication) throws UserNotFoundException {
        ModelAndView modelAndView = new ModelAndView("account-invoice");
        User userId = userService.findUserByEmail(authentication.getName());
        List<OrdersDTO> ordersByUserId = ordersService.findOrdersByUserId(userId.getId());
        Comparator<OrdersDTO> sortOrders = new Comparator<OrdersDTO>() {
            @Override
            public int compare(OrdersDTO order1, OrdersDTO order2) {
                return order2.getId() - order1.getId();
            }
        };

        Utils.sortCompare(ordersByUserId, sortOrders);
        modelAndView.addObject("orders", ordersByUserId);

        if (ordersService.findOrdersByUserId(userId.getId()).isEmpty()) {
            modelAndView.addObject("shortageOrders", true);
        }
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("invoicesNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @GetMapping("/invoices/orders/{userId}/downloadInvoice/{orderId}")
    public ModelAndView displayInvoices(@PathVariable Integer userId, @PathVariable Integer orderId, Authentication authentication)
            throws AddressNotFoundException, OrdersNotFoundException, ProductNotFoundException, OrderItemNotFoundException,
            FileNotFoundException, UserNotFoundException, DeliveryNotFoundException, JRException {
        User user = userService.findUserByEmail(authentication.getName());
        if (!user.getId().equals(userId)) {
            return new ModelAndView("redirect:/invoices");
        }
        invoiceService.generateInvoice(orderId);
        return new ModelAndView("redirect:/invoices");
    }

    @PostMapping("/invoices/add-newsletter")
    public ModelAndView addNewsletterToOrders(@Valid @ModelAttribute(name = "invoicesNewsletter")
                                                      CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult)
            throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("invoices");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:/invoices?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:/invoices?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:/invoices?success");
    }
}
