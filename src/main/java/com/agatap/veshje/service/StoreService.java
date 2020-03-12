package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateStoreDTO;
import com.agatap.veshje.controller.DTO.StoreDTO;
import com.agatap.veshje.controller.mapper.StoreDTOMapper;
import com.agatap.veshje.model.Store;
import com.agatap.veshje.repository.StoreRepository;
import com.agatap.veshje.service.exception.StoreDataInvalid;
import com.agatap.veshje.service.exception.StoreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreDTOMapper mapper;

    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll().stream()
                .map(store -> mapper.mappingToDTO(store))
                .collect(Collectors.toList());
    }

    public StoreDTO findStoreDTOById(Integer id) throws StoreNotFoundException {
        return storeRepository.findById(id)
                .map(store -> mapper.mappingToDTO(store))
                .orElseThrow(() -> new StoreNotFoundException());
    }

    public Store findStoreById(Integer id) throws StoreNotFoundException {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException());
    }

    public StoreDTO createStoreDTO(CreateUpdateStoreDTO createStoreDTO) throws StoreDataInvalid {
        if (createStoreDTO.getName() == null) {
            throw new StoreDataInvalid();
        }
        Store store = mapper.mappingToModel(createStoreDTO);
        store.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Store newStore = storeRepository.save(store);
        return mapper.mappingToDTO(newStore);
    }

    public StoreDTO updateStoreDTO(CreateUpdateStoreDTO updateStoreDTO, Integer id) throws StoreNotFoundException {
        Store store = findStoreById(id);
        store.setName(updateStoreDTO.getName());
        store.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Store updateStore = storeRepository.save(store);
        return mapper.mappingToDTO(updateStore);
    }

    public StoreDTO deleteStoreDTO(Integer id) throws StoreNotFoundException {
        Store store = findStoreById(id);
        storeRepository.delete(store);
        return mapper.mappingToDTO(store);
    }
}
