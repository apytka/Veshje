package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.FavouritesDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.UserDTO;
import com.agatap.veshje.controller.mapper.FavouritesDTOMapper;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.FavouritesRepository;
import com.agatap.veshje.repository.ProductRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@AllArgsConstructor
public class FavouritesViewController {

    private UserService userService;
    private FavouritesService favouritesService;
    private ProductService productService;
    private FavouritesDTOMapper favouritesDTOMapper;

    @GetMapping("/favourites")
    public ModelAndView displayFavourites(Authentication authentication)
            throws UserNotFoundException, FavouritesNotFoundException, ProductNotFoundException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("favourites");
        User user = userService.findUserByEmail(authentication.getName());
        Favourites favourites = favouritesService.findFavouritesByUserId(user.getId());
        List<Product> products = favourites.getProducts();

        List<SizeType> sizeType = Arrays.asList(SizeType.values());
        Map<List<String>, Product> map = new HashMap<>();
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
        return modelAndView;
    }

}
