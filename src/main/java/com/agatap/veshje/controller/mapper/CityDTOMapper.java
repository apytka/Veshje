package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CityDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateCityDTO;
import com.agatap.veshje.model.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CityDTOMapper {
    public CityDTO mappingToDTO(City city) {
        Integer countriesId = Optional.ofNullable(city.getCountry())
                .map(country -> country.getId()).orElse(null);
        List<Integer> addressesId = city.getAddress().stream()
                .map(address -> address.getId())
                .collect(Collectors.toList());
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .countryIds(countriesId)
                .addressIds(addressesId)
                .createDate(city.getCreateDate())
                .updateDate(city.getUpdateDate())
                .build();
    }

    public City mappingToModel(CreateUpdateCityDTO createCityDTO) {
        return City.builder()
                .name(createCityDTO.getName())
                .build();
    }
}
