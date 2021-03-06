package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.controller.mapper.ProductDTOMapper;
import com.agatap.veshje.model.Category;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.repository.CategoryRepository;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.service.CategoryService;
import com.agatap.veshje.service.ProductService;
import com.agatap.veshje.service.SizeService;
import com.agatap.veshje.service.exception.ProductAlreadyExistException;
import com.agatap.veshje.service.exception.ProductDataInvalidException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.SizeNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO findProductDTOById(@PathVariable String id) throws ProductNotFoundException {
        return productService.findProductDTOById(id);
    }

    @PostMapping
    public ProductDTO createProductDTO(@RequestBody CreateUpdateProductDTO createProductDTO) throws ProductDataInvalidException, ProductAlreadyExistException {
        return productService.createProductDTO(createProductDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProductDTO(@RequestBody CreateUpdateProductDTO updateProductDTO, @PathVariable String id) throws ProductNotFoundException {
        return productService.updateProductDTO(updateProductDTO, id);
    }

    @DeleteMapping("/{id}")
    public ProductDTO deleteProductDTO(@PathVariable String id) throws ProductNotFoundException {
        return productService.deleteProductDTO(id);
    }

//    @GetMapping("/search/{keyword}")
//    public List<ProductDTO> findProductsDTOByKeyword(@PathVariable String keyword) {
//        return productService.findProductsDTOByKeyword(keyword);
//    }

    @GetMapping("/random-products")
    public List<ProductDTO> randomProducts(int number) {
        return productService.randomProducts(number);
    }

    @GetMapping("/random-products-in-category")
    public List<ProductDTO> randomProductsInCategory(int number, String category) {
        return productService.randomProductsInCategory(number, category);
    }

    @GetMapping("/images/{id}")
    public List<ImageDTO> findImageByProductId(@PathVariable String id) throws ProductNotFoundException {
        return productService.findImageByProductId(id);
    }

    @GetMapping(value = "/price", params = {"minPrice", "maxPrice"})
    public List<ProductDTO> findProductsByPrice(@RequestParam(required = false) Double minPrice, Double maxPrice) {
        return productService.findProductsByPrice(minPrice, maxPrice);
    }

    @GetMapping(value = "/size", params = {"sizeType"})
    public List<ProductDTO> findProductsBySize(@MatrixVariable @RequestParam(required = false) SizeType sizeType) {
        return productService.findProductsBySize(sizeType);
    }

    @GetMapping(value = "/category", params = {"name"})
    public List<ProductDTO> findProductsByCategoryName(@RequestParam(required = false) String name) {
        return productService.findProductsByCategoryName(name);
    }

    @GetMapping("/asc")
    public List<ProductDTO> findProductOrderByPriceAsc() {
        return productService.findProductOrderByPriceAsc();
    }

    @GetMapping("/dsc")
    public List<ProductDTO> findProductOrderByPriceDsc() {
        return productService.findProductOrderByPriceDsc();
    }

    @GetMapping("/{categories}/asc")
    public List<ProductDTO> findProductOrderByCategoriesAndPriceAsc(@PathVariable String categories) {
        return productService.findProductOrderByCategoriesAndPriceAsc(categories);
    }

    @GetMapping("/sort/{category}")
    public List<ProductDTO> filterData(@PathVariable String category,
                                 @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String search,
                                 @RequestParam(required = false) List<String> color,
                                 @RequestParam(required = false) List<SizeType> sizeType,
                                 @RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice) {
        return productService.filterData(category, name, search, color, sizeType, minPrice, maxPrice);
    }
}
