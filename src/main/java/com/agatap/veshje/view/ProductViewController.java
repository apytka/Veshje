package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
import com.agatap.veshje.model.Image;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.repository.ImageRepository;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.service.ImageService;
import com.agatap.veshje.service.ProductService;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ProductViewController {

    private ProductService productService;
    private ProductRepository productRepository;
    private ImageService imageService;
    private ImageRepository imageRepository;
    private ImageDTOMapper imageDTOMapper;

    @GetMapping("/products/mini-dresses")
    public ModelAndView displayProductByMiniDresses(@ModelAttribute ProductDTO productDTO) throws UnsupportedEncodingException, ProductNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-mini");

        List<ProductDTO> productMiniDresses = productService.findProductsByCategoryName("MINI");

        List<String> byteImageList = new ArrayList<>();
        List<Product> images = productRepository.findAll();
        List<ImageDTO> collect = images.stream()
                .flatMap(im -> im.getImages().stream())
                .filter(im -> im.getId().equals(productDTO.getId()))
                .map(im -> imageDTOMapper.mappingToDTO(im))
                .collect(Collectors.toList());

        Integer productId = null;

        for (ImageDTO collectors : collect) {
            for(ProductDTO product : productMiniDresses) {
                productId = product.getId();
            if(productId.equals(productDTO.getId())) {

            byte[] encodeBase64 = Base64.encodeBase64(collectors.getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
            }
            }
        }
        modelAndView.addObject("productId", productId);
        modelAndView.addObject("collect", collect);
        modelAndView.addObject("images", images);
        modelAndView.addObject("byteImageList", byteImageList);
        modelAndView.addObject("products", productMiniDresses);
        return modelAndView;
    }

    @GetMapping("/products/mini-dresses/dress/{id}")
    public ModelAndView displayDataProduct(@PathVariable Integer id) throws ProductNotFoundException, UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("product-dress-details");
        List<ImageDTO> images = productService.findImageByProductId(id);
        List<String> byteImageList = new ArrayList<>();
        for(ImageDTO imageDTO : images) {
            byte[] encodeBase64 = Base64.encodeBase64(imageDTO.getData());
            String base64Encoded = new String(encodeBase64, "UTF-8");
            byteImageList.add(base64Encoded);
        }
        modelAndView.addObject("byteImageList", byteImageList);
        modelAndView.addObject("images", images);
        return modelAndView;
    }

}
