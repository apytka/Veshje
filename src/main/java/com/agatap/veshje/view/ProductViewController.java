package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
import com.agatap.veshje.model.CareProduct;
import com.agatap.veshje.model.Image;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.service.CompositionProductService;
import com.agatap.veshje.service.DimensionService;
import com.agatap.veshje.service.ProductService;
import com.agatap.veshje.service.exception.CareProductNotFoundException;
import com.agatap.veshje.service.exception.CompositionProductNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ProductViewController {

    private ProductService productService;
    private ProductRepository productRepository;
    private ImageDTOMapper imageDTOMapper;
    private DimensionService dimensionService;
    private CompositionProductService compositionProductService;

    @GetMapping("/products/mini-dresses")
    public ModelAndView displayProductByMiniDresses() throws UnsupportedEncodingException, ProductNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-mini");

        List<Product> productMiniDresses = productService.findProductsByCategoryNameProduct("MINI");
        Map<List<String>, Product> map = new HashMap<>();

        for(Product product : productMiniDresses) {
            List<String> byteImageList = new ArrayList<>();
            List<ImageDTO> images = productService.findImageByProductId(product.getId());
            for (ImageDTO collectors : images) {
                byte[] encodeBase64 = Base64.encodeBase64(collectors.getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
            }
            map.put(byteImageList, product);
        }

        modelAndView.addObject("map", map);
        return modelAndView;
    }

    @GetMapping("/products/mini-dresses/dress/{id}")
    public ModelAndView displayDataProduct(@PathVariable Integer id) throws ProductNotFoundException, UnsupportedEncodingException, CareProductNotFoundException, CompositionProductNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-dress-details");
        List<ImageDTO> images = productService.findImageByProductId(id);
        ProductDTO product = productService.findProductDTOById(id);
        List<String> byteImageList = new ArrayList<>();
        for(ImageDTO imageDTO : images) {
            byte[] encodeBase64 = Base64.encodeBase64(imageDTO.getData());
            String base64Encoded = new String(encodeBase64, "UTF-8");
            byteImageList.add(base64Encoded);
        }

        List<SizeType> sizeType = Arrays.asList(SizeType.values());
        List<DimensionDTO> dimensions = dimensionService.getAllDimensions();

        Map<String, String> map = new HashMap<>();
        Product productCare = productService.findProductById(id);
        List<CareProduct> careProducts = productCare.getCareProduct();

        for(CareProduct cares : careProducts) {
            List<Image> careImages = cares.getImages();
            String base64Encoded = null;
            for(Image imageData : careImages) {
                byte[] encodeBase64 = Base64.encodeBase64(imageData.getData());
                base64Encoded = new String(encodeBase64, "UTF-8");
            }
            String careProductDescription = cares.getDescription();
            map.put(base64Encoded, careProductDescription);
        }

        List<CompositionProductDTO>  compositionProduct = compositionProductService.findCompositionByProductId(id);

        modelAndView.addObject("compositionProduct", compositionProduct);
        modelAndView.addObject("dimensions", dimensions);
        modelAndView.addObject("sizeType", sizeType);
        modelAndView.addObject("product", product);
        modelAndView.addObject("map", map);
        modelAndView.addObject("byteImageList", byteImageList);
        return modelAndView;
    }

}
