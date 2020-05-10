package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CompositionProductDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCompositionProductDTO;
import com.agatap.veshje.service.CompositionProductService;
import com.agatap.veshje.service.exception.CompositionProductDataInvalidException;
import com.agatap.veshje.service.exception.CompositionProductNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compositions")
public class CompositionProductController {
    @Autowired
    private CompositionProductService compositionProductService;

    @GetMapping
    public List<CompositionProductDTO> getAllCompositionProduct() {
        return compositionProductService.getAllCompositionProduct();
    }

    @GetMapping("/{id}")
    public CompositionProductDTO findCompositionProductDTOById(@PathVariable Integer id) throws CompositionProductNotFoundException {
        return compositionProductService.findCompositionProductDTOById(id);
    }

    @PostMapping
    public CompositionProductDTO createCompositionProductDTO(@RequestBody CreateUpdateCompositionProductDTO createCompositionProductDTO)
            throws CompositionProductDataInvalidException {
        return compositionProductService.createCompositionProductDTO(createCompositionProductDTO);
    }

    @PutMapping("/{id}")
    public CompositionProductDTO updateCompositionProductDTO(@RequestBody CreateUpdateCompositionProductDTO updateCompositionProductDTO, @PathVariable Integer id)
            throws CompositionProductNotFoundException {
        return compositionProductService.updateCompositionProductDTO(updateCompositionProductDTO, id);
    }

    @DeleteMapping("/{id}")
    public CompositionProductDTO deleteCompositionProductDTO(@PathVariable Integer id) throws CompositionProductNotFoundException {
        return compositionProductService.deleteCompositionProductDTO(id);
    }

    @GetMapping("/composition/{id}")
    public List<CompositionProductDTO> findCompositionByProductId(@PathVariable String id) throws ProductNotFoundException {
        return compositionProductService.findCompositionByProductId(id);
    }
}
