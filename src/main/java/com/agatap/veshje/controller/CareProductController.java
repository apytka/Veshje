package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CareProductDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCareProductDTO;
import com.agatap.veshje.service.CareProductService;
import com.agatap.veshje.service.exception.CareProductAlreadyExistsException;
import com.agatap.veshje.service.exception.CareProductDataInvalidException;
import com.agatap.veshje.service.exception.CareProductNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/careProducts")
public class CareProductController {
    @Autowired
    private CareProductService careProductService;

    @GetMapping
    public List<CareProductDTO> getAllCareProduct() {
        return careProductService.getAllCareProduct();
    }

    @GetMapping("/{id}")
    public CareProductDTO findCareProductDTOById(@PathVariable Integer id) throws CareProductNotFoundException {
        return careProductService.findCareProductDTOById(id);
    }

    @PostMapping
    public CareProductDTO createCareProductDTO(@RequestBody CreateUpdateCareProductDTO createCareProductDTO)
            throws CareProductDataInvalidException, CareProductAlreadyExistsException {
        return careProductService.createCareProductDTO(createCareProductDTO);
    }

    @PutMapping("/{id}")
    public CareProductDTO updateCareProductDTO(@RequestBody CreateUpdateCareProductDTO updateCareProductDTO, @PathVariable Integer id)
            throws CareProductNotFoundException {
        return careProductService.updateCareProductDTO(updateCareProductDTO, id);
    }

    @DeleteMapping("/{id}")
    public CareProductDTO deleteCareProductDTO(@PathVariable Integer id) throws CareProductNotFoundException {
        return careProductService.deleteCareProductDTO(id);
    }

    @GetMapping("/cares/{id}")
    public List<CareProductDTO> findCareByProductId(@PathVariable Integer id) throws ProductNotFoundException {
        return careProductService.findCareByProductId(id);
    }
}
