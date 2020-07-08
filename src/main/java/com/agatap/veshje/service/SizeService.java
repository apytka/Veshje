package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateSizeDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.controller.mapper.SizeDTOMapper;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.model.Size;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.repository.SizeRepository;
import com.agatap.veshje.service.exception.NotEnoughProductsInStockException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.SizeDataInvalidException;
import com.agatap.veshje.service.exception.SizeNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SizeService {

    private SizeRepository sizeRepository;
    private SizeDTOMapper mapper;
    private ProductService productService;
    private ShoppingCartService shoppingCartService;

    public List<SizeDTO> getAllSizes() {
        return sizeRepository.findAll().stream()
                .map(sizes -> mapper.mappingToDTO(sizes))
                .collect(Collectors.toList());
    }

    public SizeDTO findSizeDTOById(Integer id) throws SizeNotFoundException {
        return sizeRepository.findById(id)
                .map(sizes -> mapper.mappingToDTO(sizes))
                .orElseThrow(() -> new SizeNotFoundException());
    }

    public Size findSizeById(Integer id) throws SizeNotFoundException {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new SizeNotFoundException());
    }

    public SizeDTO createSizeDTO(CreateUpdateSizeDTO createSizeDTO)
            throws SizeDataInvalidException {
        if (createSizeDTO.getQuantity().doubleValue() < 0) {
            throw new SizeDataInvalidException();
        }

        Size size = mapper.mappingToModel(createSizeDTO);
        size.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Size newSize = sizeRepository.save(size);
        return mapper.mappingToDTO(newSize);
    }

    public SizeDTO updateSizeDTO(CreateUpdateSizeDTO updateSizeDTO, Integer id)
            throws SizeNotFoundException {
        Size size = findSizeById(id);
        size.setSizeType(updateSizeDTO.getSizeType());
        size.setQuantity(updateSizeDTO.getQuantity());
        size.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Size updateSize = sizeRepository.save(size);
        return mapper.mappingToDTO(updateSize);
    }

    public SizeDTO deleteSizeDTO(Integer id) throws SizeNotFoundException {
        Size size = findSizeById(id);
        sizeRepository.delete(size);
        return mapper.mappingToDTO(size);
    }

    public Integer getQuantityBySizeTypeAndProductId(SizeType sizeType, String id) throws ProductNotFoundException, SizeNotFoundException {
        Product product = productService.findProductById(id);
//        Integer quantity = 0;
//        for(Size size : product.getSizes()) {
//            if (size.getSizeType().equals(sizeType)) {
//                quantity = size.getQuantity();
//            }
//        }
        return product.getSizes().stream()
                .filter(size -> size.getSizeType().equals(sizeType))
                .map(size -> size.getQuantity())
                .findFirst().orElseThrow(() -> new SizeNotFoundException());
    }

    public List<SizeDTO> findSizesByProductId(String id) {
        List<Size> byProductId = sizeRepository.findByProductId(id);
        return byProductId.stream()
                .map(size -> mapper.mappingToDTO(size))
                .collect(Collectors.toList());
    }

    public SizeDTO findSizeByProductIdAndSizeType(SizeType sizeType, String id) throws ProductNotFoundException, SizeNotFoundException {
        Product product = productService.findProductById(id);
        return product.getSizes().stream()
                .filter(size -> size.getSizeType().equals(sizeType))
                .map(size -> mapper.mappingToDTO(size))
                .findFirst().orElseThrow(() -> new SizeNotFoundException());
    }

    public void updateSize(ShoppingCartDTO shoppingCart) throws ProductNotFoundException, SizeNotFoundException, NotEnoughProductsInStockException {
        shoppingCartService.checkoutStock(shoppingCart);
        SizeDTO sizeByProductIdAndSizeType = findSizeByProductIdAndSizeType(shoppingCart.getSizeType(), shoppingCart.getProductId());
        Size size = findSizeById(sizeByProductIdAndSizeType.getId());
        size.setQuantity(sizeByProductIdAndSizeType.getQuantity() - shoppingCart.getQuantity());
        size.setUpdateDate(OffsetDateTime.now());
        sizeRepository.save(size);
    }
}

