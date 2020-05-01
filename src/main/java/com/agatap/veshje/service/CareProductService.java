package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CareProductDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCareProductDTO;
import com.agatap.veshje.controller.mapper.CareProductDTOMapper;
import com.agatap.veshje.model.CareProduct;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.repository.CareProductRepository;
import com.agatap.veshje.service.exception.CareProductAlreadyExistsException;
import com.agatap.veshje.service.exception.CareProductDataInvalidException;
import com.agatap.veshje.service.exception.CareProductNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CareProductService {

    private CareProductRepository careProductRepository;
    private CareProductDTOMapper mapper;
    private ProductService productService;

    public List<CareProductDTO> getAllCareProduct() {
        return careProductRepository.findAll().stream()
                .map(careProduct -> mapper.mappingToDTO(careProduct))
                .collect(Collectors.toList());
    }

    public CareProductDTO findCareProductDTOById(Integer id) throws CareProductNotFoundException {
        return careProductRepository.findById(id)
                .map(careProduct -> mapper.mappingToDTO(careProduct))
                .orElseThrow(() -> new CareProductNotFoundException());
    }

    public CareProduct findCareProductById(Integer id) throws CareProductNotFoundException {
        return careProductRepository.findById(id)
                .orElseThrow(() -> new CareProductNotFoundException());
    }

    public CareProductDTO createCareProductDTO(CreateUpdateCareProductDTO createCareProductDTO)
            throws CareProductDataInvalidException, CareProductAlreadyExistsException {
        if(careProductRepository.existsByName(createCareProductDTO.getName())) {
            throw new CareProductAlreadyExistsException();
        }
        if(createCareProductDTO.getName() == null || createCareProductDTO.getName().length() < 2) {
            throw new CareProductDataInvalidException();
        }
        CareProduct careProduct = mapper.mappingToModel(createCareProductDTO);
        careProduct.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        CareProduct newCareProduct = careProductRepository.save(careProduct);
        return mapper.mappingToDTO(newCareProduct);
    }

    public CareProductDTO updateCareProductDTO(CreateUpdateCareProductDTO updateCareProductDTO, Integer id) throws CareProductNotFoundException {
        CareProduct careProduct = findCareProductById(id);
        careProduct.setName(updateCareProductDTO.getName());
        careProduct.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        CareProduct updateCareProduct = careProductRepository.save(careProduct);
        return mapper.mappingToDTO(updateCareProduct);
    }

    public CareProductDTO deleteCareProductDTO(Integer id) throws CareProductNotFoundException {
        CareProduct careProduct = findCareProductById(id);
        careProductRepository.delete(careProduct);
        return mapper.mappingToDTO(careProduct);
    }

    public List<CareProductDTO> findCareByProductId(Integer id) throws ProductNotFoundException {
        Product productId = productService.findProductById(id);
        return productId.getCareProduct().stream()
                .map(careProduct -> mapper.mappingToDTO(careProduct))
                .collect(Collectors.toList());
    }

}