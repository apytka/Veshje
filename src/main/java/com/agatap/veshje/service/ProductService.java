package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.mapper.CategoryDTOMapper;
import com.agatap.veshje.controller.mapper.ProductDTOMapper;
import com.agatap.veshje.controller.mapper.SizeDTOMapper;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.CategoryRepository;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.repository.SizeRepository;
import com.agatap.veshje.service.exception.ProductAlreadyExistException;
import com.agatap.veshje.service.exception.ProductDataInvalidException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductDTOMapper mapper;
    private CategoryRepository categoryRepository;
    private CategoryDTOMapper categoryDTOMapper;
    private SizeRepository sizeRepository;
    private SizeDTOMapper sizeDTOMapper;

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
        product.setTypeCollection(TypeCollection.NEW);
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

    public List<ProductDTO> findProductsBySize(SizeType sizeType) {
        List<Size> collectBySizeType = sizeRepository.findAllBySizeType(sizeType);

        return collectBySizeType.stream()
                .flatMap(product -> product.getProducts().stream())
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductsByTypeCollection(TypeCollection type) {
        return productRepository.findByTypeCollection(type).stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }
    public List<ProductDTO> findProductsByPrice(Double minPrice, Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(product -> product.getPrice() >= minPrice)
                .filter(product -> product.getPrice() <= maxPrice)
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductOrderByPriceAsc() {
        return productRepository.findByOrderByPriceAsc().stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductOrderByPriceDsc() {
        return productRepository.findByOrderByPriceDesc().stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

}
