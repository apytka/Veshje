package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateCountryDTO;
import com.agatap.veshje.controller.DTO.CountryDTO;
import com.agatap.veshje.service.CountryService;
import com.agatap.veshje.service.exception.CountryAlreadyExistsException;
import com.agatap.veshje.service.exception.CountryDataInvalidException;
import com.agatap.veshje.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<CountryDTO> getAllCountry() {
        return countryService.getAllCountry();
    }

    @GetMapping("/{id}")
    public CountryDTO findCountryDTOById(@PathVariable Integer id) throws CountryNotFoundException {
        return countryService.findCountryDTOById(id);
    }

    @PostMapping
    public CountryDTO createCountryDTO(@RequestBody CreateUpdateCountryDTO createCountryDTO)
            throws CountryDataInvalidException, CountryAlreadyExistsException {
        return countryService.createCountryDTO(createCountryDTO);
    }

    @PutMapping("/{id}")
    public CountryDTO updateCountryDTO(@RequestBody CreateUpdateCountryDTO updateCountryDTO, @PathVariable Integer id)
            throws CountryNotFoundException {
        return countryService.updateCountryDTO(updateCountryDTO, id);
    }

    @DeleteMapping("/{id}")
    public CountryDTO deleteCountryDTO(@PathVariable Integer id) throws CountryNotFoundException {
        return countryService.deleteCountryDTO(id);
    }
}
