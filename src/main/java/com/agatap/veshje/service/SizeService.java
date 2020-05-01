package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateSizeDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.controller.mapper.SizeDTOMapper;
import com.agatap.veshje.model.Size;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.repository.SizeRepository;
import com.agatap.veshje.service.exception.SizeDataInvalidException;
import com.agatap.veshje.service.exception.SizeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private SizeDTOMapper mapper;

    public List<SizeDTO> getAllSizes() {
        return sizeRepository.findAll().stream()
                .map(sizes -> mapper.mappingToDTO(sizes))
                .collect(Collectors.toList());
    }

    public SizeDTO findSizeDTOById(Integer id) throws SizeNotFoundException {
        return sizeRepository.findById(id)
                .map(sizes -> mapper.mappingToDTO(sizes))
                .orElseThrow(() -> new SizeNotFoundException());
    }

    public Size findSizeById(Integer id) throws SizeNotFoundException {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new SizeNotFoundException());
    }

    public SizeDTO createSizeDTO(CreateUpdateSizeDTO createSizeDTO)
            throws SizeDataInvalidException {
        if (createSizeDTO.getQuantity().doubleValue() < 0) {
            throw new SizeDataInvalidException();
        }

        Size size = mapper.mappingToModel(createSizeDTO);
        size.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Size newSize = sizeRepository.save(size);
        return mapper.mappingToDTO(newSize);
    }

    public SizeDTO updateSizeDTO(CreateUpdateSizeDTO updateSizeDTO, Integer id)
            throws SizeNotFoundException {
        Size size = findSizeById(id);
        size.setSizeType(updateSizeDTO.getSizeType());
        size.setQuantity(updateSizeDTO.getQuantity());
        size.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Size updateSize = sizeRepository.save(size);
        return mapper.mappingToDTO(updateSize);
    }

    public SizeDTO deleteSizeDTO(Integer id) throws SizeNotFoundException {
        Size size = findSizeById(id);
        sizeRepository.delete(size);
        return mapper.mappingToDTO(size);
    }
}

