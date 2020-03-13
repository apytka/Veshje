package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateStoreDTO;
import com.agatap.veshje.controller.DTO.StoreDTO;
import com.agatap.veshje.service.StoreService;
import com.agatap.veshje.service.exception.StoreDataInvalidException;
import com.agatap.veshje.service.exception.StoreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping
    public List<StoreDTO> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{id}")
    public StoreDTO findStoreDTOById(@PathVariable Integer id) throws StoreNotFoundException {
        return storeService.findStoreDTOById(id);
    }

    @PostMapping
    public StoreDTO createStoreDTO(@RequestBody CreateUpdateStoreDTO createStoreDTO) throws StoreDataInvalidException {
        return storeService.createStoreDTO(createStoreDTO);
    }

    @PutMapping("/{id}")
    public StoreDTO updateStoreDTO(@RequestBody CreateUpdateStoreDTO updateStoreDTO, @PathVariable Integer id) throws StoreNotFoundException {
        return storeService.updateStoreDTO(updateStoreDTO, id);
    }

    @DeleteMapping("/{id}")
    public StoreDTO deleteStoreDTO(@PathVariable Integer id) throws StoreNotFoundException {
        return storeService.deleteStoreDTO(id);
    }
}
