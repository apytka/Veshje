package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.OrderItemDTO;
import com.agatap.veshje.model.User;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.OrderItemNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.ReviewDataInvalidException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ReviewsViewController {

    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private OrderItemService orderItemService;
    private ProductService productService;
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public ModelAndView displayProductsToReviews(Authentication authentication)
            throws UserNotFoundException, ProductNotFoundException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("account-order-reviews");
        User user = userService.findUserByEmail(authentication.getName());

        Map<OrderItemDTO, String> products = new HashMap<>();
        List<OrderItemDTO> orderItemForUser = orderItemService.findOrderItemForUser(user.getId());
        for (OrderItemDTO orderItemDTO : orderItemForUser) {
            if(!orderItemDTO.isAddReview()) {
                List<ImageDTO> images = productService.findImageByProductId(orderItemDTO.getProductId());
                byte[] encodeBase64 = Base64.encodeBase64(images.get(0).getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                products.put(orderItemDTO, base64Encoded);
            }
        }

        modelAndView.addObject("userId", user.getId());
        modelAndView.addObject("products", products);
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("createReview", new CreateUpdateReviewDTO());
        return modelAndView;
    }

    @PostMapping("/reviews/add-review/{orderItemId}")
        public ModelAndView addReviewToProduct(@ModelAttribute(name = "createReview") CreateUpdateReviewDTO createUpdateReviewDTO, @PathVariable Integer orderItemId)
            throws UserNotFoundException, ReviewDataInvalidException, ProductNotFoundException, OrderItemNotFoundException {
        reviewService.createReviewDTO(createUpdateReviewDTO);
        orderItemService.updateAddReview(orderItemId);
        return new ModelAndView("redirect:/reviews");
        }
}