package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CareDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCareDTO;
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
    public List<CareDTO> getAllCareProduct() {
        return careProductService.getAllCareProduct();
    }

    @GetMapping("/{id}")
    public CareDTO findCareProductDTOById(@PathVariable Integer id) throws CareProductNotFoundException {
        return careProductService.findCareProductDTOById(id);
    }

    @PostMapping
    public CareDTO createCareProductDTO(@RequestBody CreateUpdateCareDTO createCareProductDTO)
            throws CareProductDataInvalidException, CareProductAlreadyExistsException {
        return careProductService.createCareProductDTO(createCareProductDTO);
    }

    @PutMapping("/{id}")
    public CareDTO updateCareProductDTO(@RequestBody CreateUpdateCareDTO updateCareProductDTO, @PathVariable Integer id)
            throws CareProductNotFoundException {
        return careProductService.updateCareProductDTO(updateCareProductDTO, id);
    }

    @DeleteMapping("/{id}")
    public CareDTO deleteCareProductDTO(@PathVariable Integer id) throws CareProductNotFoundException {
        return careProductService.deleteCareProductDTO(id);
    }

    @GetMapping("/cares/{id}")
    public List<CareDTO> findCareByProductId(@PathVariable String id) throws ProductNotFoundException {
        return careProductService.findCareByProductId(id);
    }
}
