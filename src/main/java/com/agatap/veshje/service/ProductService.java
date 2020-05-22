package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.mapper.CategoryDTOMapper;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductDTOMapper mapper;
    private ImageDTOMapper imageDTOMapper;
    private CategoryRepository categoryRepository;
    private SizeRepository sizeRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public ProductDTO findProductDTOById(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .map(product -> mapper.mappingToDTO(product))
                .orElseThrow(() -> new ProductNotFoundException());
    }

    public Product findProductById(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    public ProductDTO createProductDTO(CreateUpdateProductDTO createProductDTO) throws ProductDataInvalidException {
        if (createProductDTO.getPrice() == null || createProductDTO.getPrice() <= 0) {
            throw new ProductDataInvalidException();
        }
        Product product = mapper.mappingToModel(createProductDTO);
        product.setCreateDate(OffsetDateTime.now());
        product.setTypeCollection(TypeCollection.NEW);
        //todo bind to foreign tables
        Product newProduct = productRepository.save(product);
        return mapper.mappingToDTO(newProduct);
    }

    public ProductDTO updateProductDTO(CreateUpdateProductDTO updateProductDTO, String id) throws ProductNotFoundException {
        Product product = findProductById(id);
        product.setName(updateProductDTO.getName());
        product.setPrice(updateProductDTO.getPrice());
        product.setDescription(updateProductDTO.getDescription());
        product.setComplementDescription(updateProductDTO.getComplementDescription());
        product.setColor(updateProductDTO.getColor());
        product.setTypeCollection(updateProductDTO.getTypeCollection());
        product.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Product updateProduct = productRepository.save(product);
        return mapper.mappingToDTO(updateProduct);
    }

    @Transactional
    public ProductDTO deleteProductDTO(String id) throws ProductNotFoundException {
        Product product = findProductById(id);
        productRepository.delete(product);
        return mapper.mappingToDTO(product);
    }

    public List<ProductDTO> findProductsBySize(SizeType sizeType) {
        List<Size> collectBySizeType = sizeRepository.findAllBySizeType(sizeType);

        return collectBySizeType.stream()
                .map(product -> product.getProduct())
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

    public List<ProductDTO> findProductsByCategoryName(String name) {
        List<Category> collectByCategory = categoryRepository.findAllByName(name);
        return collectByCategory.stream()
                .flatMap(product -> product.getProducts().stream())
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public List<Product> findProductsByCategoryNameProduct(String name) {
        List<Category> collectByCategory = categoryRepository.findAllByName(name);
        return collectByCategory.stream()
                .flatMap(product -> product.getProducts().stream())
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

    public List<ImageDTO> findImageByProductId(String id) throws ProductNotFoundException {
        Product productId = findProductById(id);
        return productId.getImages().stream()
                .map(image -> imageDTOMapper.mappingToDTO(image))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> randomProducts(int number) {
        List<ProductDTO> products = getAllProducts();
        List<ProductDTO> randomProduct = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int index = random.nextInt(products.size());
            ProductDTO productDTO = products.get(index);
            if(!randomProduct.contains(products.get(index))) {
                randomProduct.add(productDTO);
            } else {
                i--;
            }
        }
        return randomProduct;
    }
}
