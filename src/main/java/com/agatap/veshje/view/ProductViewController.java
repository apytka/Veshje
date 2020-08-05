package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.model.*;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ProductViewController {

    private final Logger LOG = LoggerFactory.getLogger(AddressViewController.class);
    private ProductService productService;
    private DimensionService dimensionService;
    private CompositionProductService compositionProductService;
    private CategoryService categoryService;
    private UserService userService;
    private ReviewService reviewService;
    private NewsletterService newsletterService;
    private ShoppingCartService shoppingCartService;
    private FilterDataService filterDataService;

    @GetMapping("/products/{category}")
    public ModelAndView displayProductByCategory(@PathVariable String category)
            throws UnsupportedEncodingException, ProductNotFoundException, CategoryNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-display-all");

        CategoryDTO categoryByName = categoryService.findCategoryByName(category);
        List<Product> productByCategory = productService.findProductsByCategoryNameProduct(categoryByName.getName());


        Map<List<String>, Product> map = new HashMap<>();
        getProductsToDisplay(map, modelAndView, productByCategory);
        modelAndView.addObject("dataCounter", productByCategory.size());
        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("productsNewsletter", new CreateUpdateNewsletterDTO());
        modelAndView.addObject("category", categoryByName.getName());
        modelAndView.addObject("sizeTypes", Arrays.asList(SizeType.values()));
        modelAndView.addObject("filterData", new FilterData());
        return modelAndView;
    }

    @GetMapping("/products/dress-details/{id}")
    public ModelAndView displayDataProduct(@PathVariable String id, Authentication authentication)
            throws ProductNotFoundException, UnsupportedEncodingException, UserNotFoundException {
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

        if (reviews.size() == 0) {
            modelAndView.addObject("existReview", true);
        }

        int current = reviews.size();
        double rateAverage = reviewService.rateAverage(id);
        double rateSizeAverage = reviewService.rateSizeAverage(id);
        double rateLengthAverage = reviewService.rateLengthAverage(id);

        List<ProductDTO> recommendedProduct = productService.randomProducts(9);
        Map<List<String>, ProductDTO> recommendedMap = new HashMap<>();
        getProductsDTOToDisplay(recommendedMap, modelAndView, recommendedProduct);

        List<Size> sizes = product.getSizes();
        shoppingCartService.findProductInShoppingCart(id);

        modelAndView.addObject("quantityProduct", shoppingCartService.quantityProductInShoppingCart());
        modelAndView.addObject("shoppingCart", new ShoppingCart());
        modelAndView.addObject("sizes", sizes);
        modelAndView.addObject("recommendedMap", recommendedMap);
        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("current", current);
        modelAndView.addObject("rateAverage", rateAverage);
        modelAndView.addObject("rateSizeAverage", rateSizeAverage);
        modelAndView.addObject("rateLengthAverage", rateLengthAverage);
        modelAndView.addObject("compositionProduct", compositionProduct);
        modelAndView.addObject("dimensions", dimensions);
        modelAndView.addObject("product", productDTO);
        modelAndView.addObject("map", map);
        modelAndView.addObject("byteImageList", byteImageList);
        modelAndView.addObject("productDetailsNewsletter", new CreateUpdateNewsletterDTO());
        return modelAndView;
    }

    @PostMapping("/products/{category}/addNewsletter")
    public ModelAndView addNewsletterToProducts(@PathVariable String category, @Valid @ModelAttribute(name = "productsNewsletter")
            CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult) throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("product-display-all");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:/products/" + category + "?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:/products/" + category + "?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:/products/" + category + "?success");
    }

    @PostMapping("/products/dress-details-newsletter/{id}")
    public ModelAndView addNewsletterToProductDetails(@PathVariable String id, @Valid @ModelAttribute(name = "productDetailsNewsletter")
            CreateUpdateNewsletterDTO createUpdateNewsletterDTO, BindingResult bindingResult) throws NewsletterAlreadyExistsException, NewsletterDataInvalidException {
        ModelAndView modelAndView = new ModelAndView("account-modify-address");
        if (bindingResult.hasErrors()) {
            LOG.warn("Binding results has errors!");
            modelAndView.addObject("message", "Incorrectly entered data in the save newsletter");
            return new ModelAndView("redirect:/products/dress-details" + id + "?error");
        }
        if (newsletterService.isNewsletterEmailExists(createUpdateNewsletterDTO.getEmail())) {
            LOG.warn("Newsletter about the email: " + createUpdateNewsletterDTO.getEmail() + " already exists in data base");
            modelAndView.addObject("message", "There is already a newsletter registered with the email provided");
            return new ModelAndView("redirect:/products/dress-details/" + id + "?error");
        }
        newsletterService.createNewsletterDTO(createUpdateNewsletterDTO);
        return new ModelAndView("redirect:/products/dress-details/" + id + "?success");
    }

//    @GetMapping("/products/{category}/search")
//    public ModelAndView displaySearchProduct(@PathVariable String category, @Param("keyword") String keyword)
//            throws UnsupportedEncodingException, ProductNotFoundException, CategoryNotFoundException {
//        ModelAndView modelAndView = new ModelAndView("product-display-all");
//        categoryService.findCategoryByName(category);
//        List<ProductDTO> productsByKeyword = productService.findProductsByKeyword(keyword);
//        Map<List<String>, ProductDTO> map = new HashMap<>();
//        getProductsDTOToDisplay(map, modelAndView, productsByKeyword);
//        modelAndView.addObject("filterData", new FilterData());
//        return modelAndView;
//    }

    @PostMapping("/products/{category}/filter")
    public ModelAndView saveFilterData(@PathVariable String category, @ModelAttribute(name = "filterData") FilterData filterData) {
        filterDataService.addFilterToList(filterData);
        return new ModelAndView("redirect:/products/" + category + "/filter");
    }

    @GetMapping("/products/{category}/filter")
    public ModelAndView displayProductFilterByCategory(@PathVariable String category) throws ProductNotFoundException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("product-display-all");
        FilterData filterData = filterDataService.getFilterData();
        List<ProductDTO> products = productService.filterData(category, filterData.getName(), filterData.getSearch(), filterData.getColor(), filterData.getSizeType(),
                filterData.getMinPrice(), filterData.getMaxPrice());
        LinkedHashMap<List<String>, ProductDTO> map = new LinkedHashMap<>();
        LinkedHashMap<List<String>, ProductDTO> collect = new LinkedHashMap<>();
        int filterDataCounter = 0;
        for (ProductDTO product : products) {
            List<String> byteImageList = new ArrayList<>();
            List<ImageDTO> images = productService.findImageByProductId(product.getId());
            for (int i = 0; i < 2; i++) {
                byte[] encodeBase64 = Base64.encodeBase64(images.get(i).getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
            }
            map.put(byteImageList, product);
            collect = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(
                            (product1, product2) -> productService.sort(product1, product2,
                                    filterData.getSort())))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            filterDataCounter++;
        }
        modelAndView.addObject("map", collect);
        if(filterData.getSearch() != null || filterData.getName() != null || filterData.getColor() != null
                || filterData.getSizeType() != null || filterData.getMinPrice() != null
                || filterData.getMaxPrice() != null || filterData.getSort() != null) {
            modelAndView.addObject("clearFilterData", true);
        }
        modelAndView.addObject("dataCounter", filterDataCounter);
        modelAndView.addObject("filterData", filterData);
        return modelAndView;
    }

    private void getProductsDTOToDisplay(Map<List<String>, ProductDTO> map, ModelAndView modelAndView, List<ProductDTO> products)
            throws ProductNotFoundException, UnsupportedEncodingException {
        for (ProductDTO product : products) {
            List<String> byteImageList = new ArrayList<>();
            List<ImageDTO> images = productService.findImageByProductId(product.getId());
            for (int i = 0; i < 2; i++) {
                byte[] encodeBase64 = Base64.encodeBase64(images.get(i).getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
            }
            map.put(byteImageList, product);

        }
        modelAndView.addObject("map", map);
    }

    private void getProductsToDisplay(Map<List<String>, Product> map, ModelAndView modelAndView, List<Product> products)
            throws ProductNotFoundException, UnsupportedEncodingException {
        for (Product product : products) {
            List<String> byteImageList = new ArrayList<>();
            List<ImageDTO> images = productService.findImageByProductId(product.getId());
            for (int i = 0; i < 2; i++) {
                byte[] encodeBase64 = Base64.encodeBase64(images.get(i).getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
            }
            map.put(byteImageList, product);

        }
        modelAndView.addObject("map", map);
    }

    @GetMapping("/products/{category}/filter/clear")
    public ModelAndView clearFilterData(@PathVariable String category) throws CategoryNotFoundException {
        categoryService.findCategoryByName(category);
        filterDataService.clearFilter();
        return new ModelAndView("redirect:/products/" + category);
    }

}
