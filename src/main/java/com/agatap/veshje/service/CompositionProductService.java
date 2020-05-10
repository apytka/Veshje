package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CompositionProductDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCompositionProductDTO;
import com.agatap.veshje.controller.mapper.CompositionProductDTOMapper;
import com.agatap.veshje.model.CompositionProduct;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.repository.CompositionProductRepository;
import com.agatap.veshje.service.exception.CompositionProductDataInvalidException;
import com.agatap.veshje.service.exception.CompositionProductNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompositionProductService {

    private CompositionProductRepository compositionProductRepository;
    private CompositionProductDTOMapper mapper;
    private ProductService productService;

    public List<CompositionProductDTO> getAllCompositionProduct() {
        return compositionProductRepository.findAll().stream()
                .map(compositionProduct -> mapper.mappingToDTO(compositionProduct))
                .collect(Collectors.toList());
    }

    public CompositionProductDTO findCompositionProductDTOById(Integer id) throws CompositionProductNotFoundException {
        return compositionProductRepository.findById(id)
                .map(compositionProduct -> mapper.mappingToDTO(compositionProduct))
                .orElseThrow(() -> new CompositionProductNotFoundException());
    }

    public CompositionProduct findCompositionProductById(Integer id) throws CompositionProductNotFoundException {
        return compositionProductRepository.findById(id)
                .orElseThrow(() -> new CompositionProductNotFoundException());
    }

    public CompositionProductDTO createCompositionProductDTO(CreateUpdateCompositionProductDTO createCompositionProductDTO)
            throws CompositionProductDataInvalidException {

        if(createCompositionProductDTO.getCompositionType() == null || createCompositionProductDTO.getCompositionPercent() == null
                || createCompositionProductDTO.getCompositionPercent().doubleValue() <= 0) {
            throw new CompositionProductDataInvalidException();
        }
        CompositionProduct compositionProduct = mapper.mappingToModel(createCompositionProductDTO);
        compositionProduct.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        CompositionProduct newCompositionProduct = compositionProductRepository.save(compositionProduct);
        return mapper.mappingToDTO(newCompositionProduct);
    }

    public CompositionProductDTO updateCompositionProductDTO(CreateUpdateCompositionProductDTO updateCompositionProductDTO, Integer id) throws CompositionProductNotFoundException {
        CompositionProduct compositionProduct = findCompositionProductById(id);
        compositionProduct.setCompositionType(updateCompositionProductDTO.getCompositionType());
        compositionProduct.setDescription(updateCompositionProductDTO.getDescription());
        compositionProduct.setCompositionPercent(updateCompositionProductDTO.getCompositionPercent());
        compositionProduct.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        CompositionProduct updateCompositionProduct = compositionProductRepository.save(compositionProduct);
        return mapper.mappingToDTO(updateCompositionProduct);
    }

    public CompositionProductDTO deleteCompositionProductDTO(Integer id) throws CompositionProductNotFoundException {
        CompositionProduct compositionProduct = findCompositionProductById(id);
        compositionProductRepository.delete(compositionProduct);
        return mapper.mappingToDTO(compositionProduct);
    }

    public List<CompositionProductDTO> findCompositionByProductId(String id) throws ProductNotFoundException {
        Product product = productService.findProductById(id);
        return product.getComposition().stream()
                .map(composition -> mapper.mappingToDTO(composition))
                .collect(Collectors.toList());
    }
}
