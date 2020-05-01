package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateDimensionDTO;
import com.agatap.veshje.controller.DTO.DimensionDTO;
import com.agatap.veshje.controller.mapper.DimensionDTOMapper;
import com.agatap.veshje.model.Dimension;
import com.agatap.veshje.repository.DimensionRepository;
import com.agatap.veshje.service.exception.DimensionAlreadyExistsException;
import com.agatap.veshje.service.exception.DimensionDataInvalidException;
import com.agatap.veshje.service.exception.DimensionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DimensionService {
    @Autowired
    private DimensionRepository dimensionRepository;
    @Autowired
    private DimensionDTOMapper mapper;

    public List<DimensionDTO> getAllDimensions() {
        return dimensionRepository.findAll().stream()
                .map(dimensions -> mapper.mappingToDTO(dimensions))
                .collect(Collectors.toList());
    }

    public DimensionDTO findDimensionDTOById(Integer id) throws DimensionNotFoundException {
        return dimensionRepository.findById(id)
                .map(dimensions -> mapper.mappingToDTO(dimensions))
                .orElseThrow(() -> new DimensionNotFoundException());
    }

    public Dimension findDimensionById(Integer id) throws DimensionNotFoundException {
        return dimensionRepository.findById(id)
                .orElseThrow(() -> new DimensionNotFoundException());
    }

    public DimensionDTO createDimensionDTO(CreateUpdateDimensionDTO createDimensionDTO)
            throws DimensionDataInvalidException, DimensionAlreadyExistsException {
        if (createDimensionDTO.getBust() == null || createDimensionDTO.getWaist() == null ||
                createDimensionDTO.getHips() == null) {
            throw new DimensionDataInvalidException();
        }
        if(dimensionRepository.existsByBustAndWaistAndHips(createDimensionDTO.getBust(), createDimensionDTO.getWaist(),
                createDimensionDTO.getHips())) {
            throw new DimensionAlreadyExistsException();    
        }
        Dimension dimension = mapper.mappingToModel(createDimensionDTO);
        dimension.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Dimension newDimension = dimensionRepository.save(dimension);
        return mapper.mappingToDTO(newDimension);
    }

    public DimensionDTO updateDimensionDTO(CreateUpdateDimensionDTO updateDimensionDTO, Integer id)
            throws DimensionNotFoundException {
        Dimension dimension = findDimensionById(id);
        dimension.setBust(updateDimensionDTO.getBust());
        dimension.setWaist(updateDimensionDTO.getWaist());
        dimension.setHips(updateDimensionDTO.getHips());
        dimension.setSizeType(updateDimensionDTO.getSizeType());
        dimension.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Dimension updateDimension = dimensionRepository.save(dimension);
        return mapper.mappingToDTO(updateDimension);
    }

    public DimensionDTO deleteDimensionDTO(Integer id) throws DimensionNotFoundException {
        Dimension dimension = findDimensionById(id);
        dimensionRepository.delete(dimension);
        return mapper.mappingToDTO(dimension);
    }
}
