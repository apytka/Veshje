package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.service.ProductService;
import com.agatap.veshje.service.exception.ProductAlreadyExistException;
import com.agatap.veshje.service.exception.ProductDataInvalidException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
