package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateSizeDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.service.SizeService;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.SizeDataInvalidException;
import com.agatap.veshje.service.exception.SizeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public List<SizeDTO> getAllSizes() {
        return sizeService.getAllSizes();
    }

    @GetMapping("/{id}")
    public SizeDTO findSizeDTOById(@PathVariable Integer id) throws SizeNotFoundException {
        return sizeService.findSizeDTOById(id);
    }

    @PostMapping
    public SizeDTO createSizeDTO(@RequestBody CreateUpdateSizeDTO createSizeDTO)
            throws SizeDataInvalidException {
        return sizeService.createSizeDTO(createSizeDTO);
    }

    @PutMapping("/{id}")
    public SizeDTO updateSizeDTO(@RequestBody CreateUpdateSizeDTO updateSizeDTO, @PathVariable Integer id)
            throws SizeNotFoundException {
        return sizeService.updateSizeDTO(updateSizeDTO, id);
    }

    @DeleteMapping("/{id}")
    public SizeDTO deleteSizeDTO(@PathVariable Integer id) throws SizeNotFoundException {
        return sizeService.deleteSizeDTO(id);
    }

    @GetMapping("/quantity/{id}/{sizeType}")
    public Integer findQuantityBySizeTypeAndProductId(@PathVariable SizeType sizeType, @PathVariable String id)
            throws ProductNotFoundException, SizeNotFoundException {
        return sizeService.getQuantityBySizeTypeAndProductId(sizeType, id);
    }

    @GetMapping("/size-by-products/{id}")
    public List<SizeDTO> findSizesByProductId(@PathVariable String id) {
        return sizeService.findSizesByProductId(id);
    }

    @GetMapping("/size/{id}/{sizeType}")
    public SizeDTO findSizeByProductIdAndSizeType(@PathVariable SizeType sizeType, @PathVariable String id)
            throws ProductNotFoundException, SizeNotFoundException {
        return sizeService.findSizeByProductIdAndSizeType(sizeType, id);
    }
}
