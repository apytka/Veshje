package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.mapper.ProductDTOMapper;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.TypeCollection;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.service.exception.ProductAlreadyExistException;
import com.agatap.veshje.service.exception.ProductDataInvalidException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDTOMapper mapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public ProductDTO findProductDTOById(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .map(product -> mapper.mappingToDTO(product))
                .orElseThrow(() -> new ProductNotFoundException());
    }

    public Product findProductOById(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    public ProductDTO createProductDTO(CreateUpdateProductDTO createProductDTO) throws ProductDataInvalidException, ProductAlreadyExistException {
        if(productRepository.existsByName(createProductDTO.getName())) {
            throw new ProductAlreadyExistException();
        }
        if(createProductDTO.getPrice() == null || createProductDTO.getPrice().doubleValue() <= 0) {
            throw new ProductDataInvalidException();
        }
        Product product = mapper.mappingToModel(createProductDTO);
        product.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Product newProduct = productRepository.save(product);
        return mapper.mappingToDTO(newProduct);
    }

    public ProductDTO updateProductDTO(CreateUpdateProductDTO updateProductDTO, Integer id) throws ProductNotFoundException {
        Product product = findProductOById(id);
        product.setName(updateProductDTO.getName());
        product.setPrice(updateProductDTO.getPrice());
        product.setDescription(updateProductDTO.getDescription());
        product.setTypeCollection(updateProductDTO.getTypeCollection());
        product.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Product updateProduct = productRepository.save(product);
        return mapper.mappingToDTO(updateProduct);
    }

    public ProductDTO deleteProductDTO(Integer id) throws ProductNotFoundException {
        Product product = findProductOById(id);
        productRepository.delete(product);
        return mapper.mappingToDTO(product);
    }
}
