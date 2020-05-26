package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.FavouritesRepository;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@AllArgsConstructor
public class FavouritesViewController {

    private final Logger LOG = LoggerFactory.getLogger(AddressViewController.class);

    private UserService userService;
    private FavouritesService favouritesService;
    private ProductService productService;
    private FavouritesRepository favouritesRepository;
    private ShoppingCartService shoppingCartService;
    private NewsletterService newsletterService;

    @GetMapping("/favourites")
    public ModelAndView displayFavourites(Authentication authentication)
            throws UserNotFoundException, FavouritesNotFoundException, ProductNotFoundException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("favourites");
        User user = userService.findUserByEmail(authentication.getName());

        if (!favouritesRepository.existsByUserId(user.getId())) {
            modelAndView.addObject("exist", true);
        } else {
            Favourites favourites = favouritesService.findFavouritesByUserId(user.getId());
            List<SizeType> sizeType = Arrays.asList(SizeType.values());
            Map<String, Product> map = new HashMap<>();

            if (favourites != null) {
                List<Product> products = favourites.getProducts();

                for (Product product : products) {
                    String base64Encoded = null;
                    List<ImageDTO> images = productService.findImageByProductId(product.getId());

                    for (ImageDTO image : images) {
                        byte[] encodeBase64 = Base64.encodeBase64(image.getData());
                        base64Encoded = new String(encodeBase64, "UTF-8");
                    }
                    map.put(base64Encoded, product);
                }
                modelAndView.addObject("cartNewsletter", new CreateUpdateNewsletterDTO());
                modelAndView.addObject("shoppingCart", new ShoppingCart());
                modelAndView.addObject("sizeType", sizeType);
                modelAndView.addObject("map", map);
            }
        }

        List<ProductDTO> recommendedProduct = productService.randomProducts(9);
        Map<List<String>,ProductDTO> recommendedMap = new HashMap<>();
        for(ProductDTO productRandom : recommendedProduct) {
            List<String> byteImageRecommendedProduct = new ArrayList<>();
            List<ImageDTO> imagesRecommendedProduct = productService.findImageByProductId(productRandom.getId());
            for(ImageDTO imageDTO : imagesRecommendedProduct) {
                byte[] encodeBase64 = Base64.encodeBase64(imageDTO.getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageRecommendedProduct.add(base64Encoded);
            }
            recommendedMap.put(byteImageRecommendedProduct, productRandom);
        }
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("recommendedMap", recommendedMap);
        return modelAndView;
    }

    @GetMapping("/favourites/delete-product")
    public ModelAndView deleteProduct(@RequestParam String id, Authentication authentication) throws UserNotFoundException, ProductNotFoundException, FavouritesNotFoundException {
        ModelAndView modelAndView = new ModelAndView("redirect:/favourites");
        User user = userService.findUserByEmail(authentication.getName());
        Favourites favourites = user.getFavourites();
        List<Product> products = favourites.getProducts();

        products.removeIf(next -> next.getId().equals(id));
        favourites.getProducts().removeIf(next -> next.getId().equals(id));
        favouritesRepository.save(favourites);

        if (products.isEmpty()) {
            Favourites favouritesId = favouritesService.findFavouritesByUserId(user.getId());
            favouritesService.deleteFavouritesDTO(favouritesId.getId());
        }
        return modelAndView;
    }

    @GetMapping("/favourites/add")
    public ModelAndView addProduct(@RequestParam String id, Authentication authentication) throws UserNotFoundException, ProductNotFoundException {
        User user = userService.findUserByEmail(authentication.getName());
        CreateUpdateFavouritesDTO createUpdateFavouritesDTO = new CreateUpdateFavouritesDTO();
        if (!favouritesRepository.existsByUserId(user.getId())) {
            favouritesService.createFavouritesDTO(createUpdateFavouritesDTO, user.getId());
        }
        Favourites favourites = user.getFavourites();
        Product product = productService.findProductById(id);
        List<Product> products = favourites.getProducts();

        product.getFavourites().add(favourites);
        products.add(product);

        favouritesRepository.save(favourites);

        return new ModelAndView("redirect:/products/dress-details/" + id);
    }

    @PostMapping("/add-cart-newsletter")
    public ModelAndView addAddressNewsletter(@Valid @ModelAttribute(name = "cartNewsletter")
                                                     CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult) throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account-add-address");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:favourites?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:favourites?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:favourites?success");
    }
}
