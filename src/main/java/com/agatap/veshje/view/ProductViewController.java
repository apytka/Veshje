package com.agatap.veshje.view;

import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
import com.agatap.veshje.model.Image;
import com.agatap.veshje.repository.ImageRepository;
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
    private ImageService imageService;
    private ImageRepository imageRepository;
    private ImageDTOMapper imageDTOMapper;

    @GetMapping("/products/mini-dresses")
    public ModelAndView displayProductByMiniDresses(@ModelAttribute ProductDTO productDTO) throws UnsupportedEncodingException, ProductNotFoundException {
        ModelAndView modelAndView = new ModelAndView("product-mini");

        List<ProductDTO> productMiniDresses = productService.findProductsByCategoryName("MINI");

        List<String> byteImageList = new ArrayList<>();
        List<ImageDTO> images = imageService.getListImages();
        List<Integer> collect2 = images.stream()
                .map(product -> product.getProductIds())
                .collect(Collectors.toList());

        List<Integer> test = new ArrayList<>();
        for(Integer collectors2 : collect2) {
            if(collectors2.equals(productDTO.getId())) {
                test.add(collectors2);
            }
        }

        List<ImageDTO> collect1 = images.stream()
                .filter(product -> product.getProductIds().equals(productDTO.getId()))
                .collect(Collectors.toList());

        List<Integer> collect = images.stream()
                .map(product -> product.getProductIds())
                .collect(Collectors.toList());
        for (ImageDTO collectors : collect1) {
                byte[] encodeBase64 = Base64.encodeBase64(collectors.getData());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                byteImageList.add(base64Encoded);
        }

//        for (Integer coll : collect) {
////            if (productDTO.getId().equals(coll)) {
//            for (int i = 0; i < images.size(); i++) {
//                byte[] encodeBase64 = Base64.encodeBase64(images.get(i).getData());
//                String base64Encoded = new String(encodeBase64, "UTF-8");
//                byteImageList.add(base64Encoded);
////            }
//        }
//            modelAndView.addObject("collect1", collect1);
//            modelAndView.addObject("collect", collect);
//            modelAndView.addObject("byteImageList", byteImageList);
//        }
        modelAndView.addObject("test", test);
        modelAndView.addObject("collect", collect);
        modelAndView.addObject("collect2", collect2);
        modelAndView.addObject("byteImageList", byteImageList);
        modelAndView.addObject("products", productMiniDresses);
        return modelAndView;
    }

}
