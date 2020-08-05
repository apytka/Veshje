package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
import com.agatap.veshje.controller.mapper.ProductDTOMapper;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.CategoryRepository;
import com.agatap.veshje.repository.ProductRepository;
import com.agatap.veshje.repository.SizeRepository;
import com.agatap.veshje.service.exception.ProductDataInvalidException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    private CategoryService categoryService;

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

    public List<ProductDTO> findProductsDTOByKeyword(String keyword) {
        return productRepository.findAll(keyword).stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductsByKeyword(String keyword) {
        return productRepository.findAll(keyword).stream()
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductsBySize(SizeType sizeType) {
        List<Size> collectBySizeType = sizeRepository.findAllBySizeType(sizeType);

        return collectBySizeType.stream()
                .map(product -> product.getProduct())
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
            if (!randomProduct.contains(products.get(index))) {
                randomProduct.add(productDTO);
            } else {
                i--;
            }
        }
        return randomProduct;
    }

    public List<ProductDTO> randomProductsInCategory(int number, String category) {
        List<ProductDTO> products = findProductsByCategoryName(category);
        List<ProductDTO> randomProduct = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int index = random.nextInt(products.size());
            ProductDTO productDTO = products.get(index);
            if (!randomProduct.contains(products.get(index))) {
                randomProduct.add(productDTO);
            } else {
                i--;
            }
        }
        return randomProduct;
    }

    public List<ProductDTO> findProductOrderByCategoriesAndPriceAsc(@PathVariable String categories) {
        return productRepository.findByOrderByPriceAsc().stream()
                .map(product -> mapper.mappingToDTO(product))
                .filter(category -> categoryRepository.existsByName(categories))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> filterData(String category, String name, String search, List<String> color, List<SizeType> sizeType,
                                       Double minPrice, Double maxPrice) {
        return findProductsByCategoryName(category).stream()
                .filter(product -> name == null || product.getName().contains(name) || name.equals(""))
                .filter(product -> search == null || product.getName().contains(search) || product.getDescription().contains(search)
                        || product.getColor().contains(search))
                .filter(product -> sizeType == null || sizeType.isEmpty() || checkQuantityAvailable(sizeType, product.getId()))
                .filter(product -> color == null || color.isEmpty() || checkColor(color, product.getColor()))
                .filter(product -> minPrice == null || product.getPrice() >= minPrice)
                .filter(product -> maxPrice == null || product.getPrice() <= maxPrice)
                .filter(product -> minPrice == null || maxPrice == null || (product.getPrice() >= minPrice && product.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }

    private boolean checkColor(List<String> inputColors, String productColor) {
        for (String color : inputColors) {
            if (productColor.contains(color)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkQuantityAvailable(List<SizeType> sizeType, String id) {
        for (SizeType size : sizeType) {
            List<Size> sizesByProductId = sizeRepository.findByProductId(id);
            for (Size sizes : sizesByProductId) {
                if (sizes.getSizeType().equals(size)) {
                    if (sizes.getQuantity() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int sort(ProductDTO product1, ProductDTO product2, String sortField) {
        if (sortField != null) {
            if (sortField.contains("price-desc")) {
                return product2.getPrice().compareTo(product1.getPrice());
            } else if (sortField.contains("price-asc")) {
                return product1.getPrice().compareTo(product2.getPrice());
            } else if (sortField.contains("date-desc")) {
                return product2.getCreateDate().compareTo(product1.getCreateDate());
            } else if (sortField.contains("name-asc")) {
                return product1.getName().compareTo(product2.getName());
            } else if (sortField.contains("name-desc")) {
                return product2.getName().compareTo(product1.getName());
            }
        } else {
            return product1.getId().compareTo(product2.getId());
        }
        return 0;
    }
}
