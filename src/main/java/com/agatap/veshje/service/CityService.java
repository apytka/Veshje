package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CityDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCityDTO;
import com.agatap.veshje.controller.mapper.CityDTOMapper;
import com.agatap.veshje.model.City;
import com.agatap.veshje.repository.CityRepository;
import com.agatap.veshje.service.exception.CityAlreadyExistsException;
import com.agatap.veshje.service.exception.CityDataInvalidException;
import com.agatap.veshje.service.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityDTOMapper mapper;

    public List<CityDTO> getAllCity() {
        return cityRepository.findAll().stream()
                .map(city -> mapper.mappingToDTO(city))
                .collect(Collectors.toList());
    }

    public CityDTO findCityDTOById(Integer id) throws CityNotFoundException {
        return cityRepository.findById(id)
                .map(city -> mapper.mappingToDTO(city))
                .orElseThrow(() -> new CityNotFoundException());
    }

    public City findCityOById(Integer id) throws CityNotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException());
    }

    public CityDTO createCityDTO(CreateUpdateCityDTO createCityDTO)
            throws CityDataInvalidException, CityAlreadyExistsException {
        if(cityRepository.existsByName(createCityDTO.getName())) {
            throw new CityAlreadyExistsException();
        }
        if(createCityDTO.getName() == null || createCityDTO.getName().length() < 2) {
            throw new CityDataInvalidException();
        }
        City city = mapper.mappingToModel(createCityDTO);
        city.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        City newCity = cityRepository.save(city);
        return mapper.mappingToDTO(newCity);
    }

    public CityDTO updateCityDTO(CreateUpdateCityDTO updateCityDTO, Integer id) throws CityNotFoundException {
        City city = findCityOById(id);
        city.setName(updateCityDTO.getName());
        city.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        City updateCity = cityRepository.save(city);
        return mapper.mappingToDTO(updateCity);
    }

    public CityDTO deleteCityDTO(Integer id) throws CityNotFoundException {
        City city = findCityOById(id);
        cityRepository.delete(city);
        return mapper.mappingToDTO(city);
    }
}