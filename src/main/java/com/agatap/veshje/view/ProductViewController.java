package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.CategoryNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.ReviewNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@AllArgsConstructor
public class ProductViewController {

    private ProductService productService;
    private DimensionService dimensionService;
    private CompositionProductService compositionProductService;
    private CategoryService categoryService;
    private UserService userService;
    private ReviewService reviewService;
    
    @GetMapping("/products/{category}")
    public ModelAndView displayProductByMiniDresses(@PathVariable String category)
            throws UnsupportedEncodingException, ProductNotFoundException, CategoryNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-display-all");

        CategoryDTO categoryByName = categoryService.findCategoryByName(category);
        List<Product> productByCategory = productService.findProductsByCategoryNameProduct(categoryByName.getName());

        Map<List<String>, Product> map = new HashMap<>();
        for (Product product : productByCategory) {
            List<String> byteImageList = new ArrayList<>();
            List<ImageDTO> images = productService.findImageByProductId(product.getId());
            for (ImageDTO image : images) {
                byte[] encodeBase64 = Base64.encodeBase64(image.getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
            }
            map.put(byteImageList, product);
        }

        modelAndView.addObject("map", map);
        return modelAndView;
    }

    @GetMapping("/products/dress-details/{id}")
    public ModelAndView displayDataProduct(@PathVariable Integer id, Authentication authentication)
            throws ProductNotFoundException, UnsupportedEncodingException, UserNotFoundException, ReviewNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-dress-details");

        if (authentication != null) {
            User user = userService.findUserByEmail(authentication.getName());
            Favourites favourites = user.getFavourites();
            if (favourites != null) {
                List<Product> products = favourites.getProducts();
                for (Product product : products) {
                    if (product.getId().equals(id)) {
                        modelAndView.addObject("exist", true);
                    }
                }
            }
        }

        List<ImageDTO> images = productService.findImageByProductId(id);
        ProductDTO productDTO = productService.findProductDTOById(id);
        List<String> byteImageList = new ArrayList<>();
        for (ImageDTO imageDTO : images) {
            byte[] encodeBase64 = Base64.encodeBase64(imageDTO.getData());
            String base64Encoded = new String(encodeBase64, "UTF-8");
            byteImageList.add(base64Encoded);
        }

        List<SizeType> sizeType = Arrays.asList(SizeType.values());
        List<DimensionDTO> dimensions = dimensionService.getAllDimensions();

        Map<String, String> map = new HashMap<>();
        Product productCare = productService.findProductById(id);
        List<Care> careProducts = productCare.getCares();

        for (Care cares : careProducts) {
            List<Image> careImages = cares.getImages();
            String base64Encoded = null;
            for (Image imageData : careImages) {
                byte[] encodeBase64 = Base64.encodeBase64(imageData.getData());
                base64Encoded = new String(encodeBase64, "UTF-8");
            }
            String careProductDescription = cares.getDescription();
            map.put(base64Encoded, careProductDescription);
        }

        List<CompositionProductDTO> compositionProduct = compositionProductService.findCompositionByProductId(id);

        Product product = productService.findProductById(id);
        List<Review> reviews = product.getReviews();

        if(reviews.size() == 0) {
            modelAndView.addObject("existReview", true);
        }

        int current = reviews.size();
        double rateAverage = reviewService.rateAverage(id);
        double rateSizeAverage = reviewService.rateSizeAverage(id);
        double rateLengthAverage = reviewService.rateLengthAverage(id);

        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("current", current);
        modelAndView.addObject("rateAverage", rateAverage);
        modelAndView.addObject("rateSizeAverage", rateSizeAverage);
        modelAndView.addObject("rateLengthAverage", rateLengthAverage);
        modelAndView.addObject("compositionProduct", compositionProduct);
        modelAndView.addObject("dimensions", dimensions);
        modelAndView.addObject("sizeType", sizeType);
        modelAndView.addObject("product", productDTO);
        modelAndView.addObject("map", map);
        modelAndView.addObject("byteImageList", byteImageList);
        return modelAndView;
    }

}
