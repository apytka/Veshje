package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.model.Category;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.model.TypeCollection;
import com.agatap.veshje.service.ProductService;
import com.agatap.veshje.service.exception.ProductAlreadyExistException;
import com.agatap.veshje.service.exception.ProductDataInvalidException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO findProductDTOById(@PathVariable Integer id) throws ProductNotFoundException {
        return productService.findProductDTOById(id);
    }

    @PostMapping
    public ProductDTO createProductDTO(@RequestBody CreateUpdateProductDTO createProductDTO) throws ProductDataInvalidException, ProductAlreadyExistException {
        return productService.createProductDTO(createProductDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProductDTO(@RequestBody CreateUpdateProductDTO updateProductDTO, @PathVariable Integer id) throws ProductNotFoundException {
        return productService.updateProductDTO(updateProductDTO, id);
    }

    @DeleteMapping("/{id}")
    public ProductDTO deleteProductDTO(@PathVariable Integer id) throws ProductNotFoundException {
        return productService.deleteProductDTO(id);
    }

    @GetMapping("/images/{id}")
    public List<ImageDTO> findImageByProductId(@PathVariable Integer id) throws ProductNotFoundException {
        return productService.findImageByProductId(id);
    }

    @GetMapping(value = "/price", params = {"minPrice", "maxPrice"})
    public List<ProductDTO> findProductsByPrice(@RequestParam(required = false) Double minPrice, Double maxPrice) {
        return productService.findProductsByPrice(minPrice, maxPrice);
    }

    @GetMapping(value = "/type", params = {"type"})
    public List<ProductDTO> findProductsByTypeCollection(@RequestParam(required = false) TypeCollection type) {
        return productService.findProductsByTypeCollection(type);
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

}
