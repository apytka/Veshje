package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CountryDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCountryDTO;
import com.agatap.veshje.model.Country;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryDTOMapper {
    public CountryDTO mappingToDTO(Country country) {
        List<Integer> citiesId = country.getCities().stream()
                .map(c -> country.getId())
                .collect(Collectors.toList());
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .cityIds(citiesId)
                .createDate(country.getCreateDate())
                .updateDate(country.getUpdateDate())
                .build();
    }

    public Country mappingToModel(CreateUpdateCountryDTO createCountryDTO) {
        return Country.builder()
                .name(createCountryDTO.getName())
                .build();
    }
}
