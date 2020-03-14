package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CityDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCityDTO;
import com.agatap.veshje.service.CityService;
import com.agatap.veshje.service.exception.CityAlreadyExistsException;
import com.agatap.veshje.service.exception.CityDataInvalidException;
import com.agatap.veshje.service.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public List<CityDTO> getAllCity() {
        return cityService.getAllCity();
    }

    @GetMapping("/{id}")
    public CityDTO findCityDTOById(@PathVariable Integer id) throws CityNotFoundException {
        return cityService.findCityDTOById(id);
    }

    @PostMapping
    public CityDTO createCityDTO(@RequestBody CreateUpdateCityDTO createCityDTO)
            throws CityDataInvalidException, CityAlreadyExistsException {
        return cityService.createCityDTO(createCityDTO);
    }

    @PutMapping("/{id}")
    public CityDTO updateCityDTO(@RequestBody CreateUpdateCityDTO updateCityDTO, @PathVariable Integer id)
            throws CityNotFoundException {
        return cityService.updateCityDTO(updateCityDTO, id);
    }

    @DeleteMapping("/{id}")
    public CityDTO deleteCityDTO(@PathVariable Integer id) throws CityNotFoundException {
        return cityService.deleteCityDTO(id);
    }
}
