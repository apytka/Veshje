package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateDimensionDTO;
import com.agatap.veshje.controller.DTO.DimensionDTO;
import com.agatap.veshje.service.DimensionService;
import com.agatap.veshje.service.exception.DimensionAlreadyExistsException;
import com.agatap.veshje.service.exception.DimensionDataInvalidException;
import com.agatap.veshje.service.exception.DimensionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dimensions")
public class DimensionController {
    @Autowired
    private DimensionService dimensionService;

    @GetMapping
    public List<DimensionDTO> getAllDimensions() {
        return dimensionService.getAllDimensions();
    }

    @GetMapping("/{id}")
    public DimensionDTO findDimensionDTOById(@PathVariable Integer id) throws DimensionNotFoundException {
        return dimensionService.findDimensionDTOById(id);
    }

    @PostMapping
    public DimensionDTO createDimensionDTO(@RequestBody CreateUpdateDimensionDTO createDimensionDTO)
            throws DimensionDataInvalidException, DimensionAlreadyExistsException {
        return dimensionService.createDimensionDTO(createDimensionDTO);
    }

    @PutMapping("/{id}")
    public DimensionDTO updateDimensionDTO(@RequestBody CreateUpdateDimensionDTO updateDimensionDTO, @PathVariable Integer id)
            throws DimensionNotFoundException {
        return dimensionService.updateDimensionDTO(updateDimensionDTO, id);
    }

    @DeleteMapping("/{id}")
    public DimensionDTO deleteDimensionDTO(@PathVariable Integer id) throws DimensionNotFoundException {
        return dimensionService.deleteDimensionDTO(id);
    }
}
