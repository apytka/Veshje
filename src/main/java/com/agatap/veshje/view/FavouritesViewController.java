package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.FavouritesRepository;
import com.agatap.veshje.service.FavouritesService;
import com.agatap.veshje.service.ProductService;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.FavouritesNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@AllArgsConstructor
public class FavouritesViewController {

    private UserService userService;
    private FavouritesService favouritesService;
    private ProductService productService;
    private FavouritesRepository favouritesRepository;

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
            Map<List<String>, Product> map = new HashMap<>();

            if (favourites != null) {
                List<Product> products = favourites.getProducts();

                for (Product product : products) {
                    List<String> byteImageList = new ArrayList<>();
                    List<ImageDTO> images = productService.findImageByProductId(product.getId());

                    for (ImageDTO image : images) {
                        byte[] encodeBase64 = Base64.encodeBase64(image.getData());
                        String base64Encoded = new String(encodeBase64, "UTF-8");
                        byteImageList.add(base64Encoded);
                    }
                    map.put(byteImageList, product);
                }
                modelAndView.addObject("sizeType", sizeType);
                modelAndView.addObject("map", map);
            }
        }
        return modelAndView;
    }

    @GetMapping("/favourites/delete-product")
    public ModelAndView deleteProduct(@RequestParam Integer id, Authentication authentication) throws UserNotFoundException, ProductNotFoundException, FavouritesNotFoundException {
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
    public ModelAndView addProduct(@ModelAttribute(name = "createFavourites") CreateUpdateFavouritesDTO createUpdateFavouritesDTO,
                                   @RequestParam Integer id, Authentication authentication) throws UserNotFoundException, ProductNotFoundException {
        User user = userService.findUserByEmail(authentication.getName());
        if (!favouritesRepository.existsByUserId(user.getId())) {
            favouritesService.createFavouritesDTO(createUpdateFavouritesDTO, user.getId());
        }
        Favourites favourites = user.getFavourites();
        Product product = productService.findProductById(id);
        List<Product> products = favourites.getProducts();

        product.getFavourites().add(favourites);
        products.add(product);

        favouritesRepository.save(favourites);

        return new ModelAndView("redirect:/favourites");
    }
}
