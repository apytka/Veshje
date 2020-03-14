package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateCountryDTO;
import com.agatap.veshje.controller.DTO.CountryDTO;
import com.agatap.veshje.controller.mapper.CountryDTOMapper;
import com.agatap.veshje.model.Country;
import com.agatap.veshje.repository.CountryRepository;
import com.agatap.veshje.service.exception.CountryAlreadyExistsException;
import com.agatap.veshje.service.exception.CountryDataInvalidException;
import com.agatap.veshje.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryDTOMapper mapper;

    public List<CountryDTO> getAllCountry() {
        return countryRepository.findAll().stream()
                .map(country -> mapper.mappingToDTO(country))
                .collect(Collectors.toList());
    }

    public CountryDTO findCountryDTOById(Integer id) throws CountryNotFoundException {
        return countryRepository.findById(id)
                .map(country -> mapper.mappingToDTO(country))
                .orElseThrow(() -> new CountryNotFoundException());
    }

    public Country findCountryOById(Integer id) throws CountryNotFoundException {
        return countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException());
    }

    public CountryDTO createCountryDTO(CreateUpdateCountryDTO createCountryDTO)
            throws CountryDataInvalidException, CountryAlreadyExistsException {
        if(countryRepository.existsByName(createCountryDTO.getName())) {
            throw new CountryAlreadyExistsException();
        }
        if(createCountryDTO.getName() == null) {
            throw new CountryDataInvalidException();
        }
        Country country = mapper.mappingToModel(createCountryDTO);
        country.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Country newCountry = countryRepository.save(country);
        return mapper.mappingToDTO(newCountry);
    }

    public CountryDTO updateCountryDTO(CreateUpdateCountryDTO updateCountryDTO, Integer id) throws CountryNotFoundException {
        Country country = findCountryOById(id);
        country.setName(updateCountryDTO.getName());
        country.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Country updateCountry = countryRepository.save(country);
        return mapper.mappingToDTO(updateCountry);
    }

    public CountryDTO deleteCountryDTO(Integer id) throws CountryNotFoundException {
        Country country = findCountryOById(id);
        countryRepository.delete(country);
        return mapper.mappingToDTO(country);
    }
}