package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.AddressDataDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateAddressDataDTO;
import com.agatap.veshje.service.AddressDataService;
import com.agatap.veshje.service.exception.AddressDataInvalidException;
import com.agatap.veshje.service.exception.AddressNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address-data")
public class AddressDataController {

    @Autowired
    private AddressDataService addressDataService;

    @GetMapping
    public List<AddressDataDTO> getAllAddressData() {
        return addressDataService.getAllAddressData();
    }

    @GetMapping("/{id}")
    public AddressDataDTO findAddressDTOById(@PathVariable Integer id) throws AddressNotFoundException {
        return addressDataService.findAddressDataDTOById(id);
    }

    @PostMapping
    public AddressDataDTO createAddressDTO(@RequestBody CreateUpdateAddressDataDTO createUpdateAddressDataDTO)
            throws AddressDataInvalidException, UserNotFoundException {
        return addressDataService.createAddressDTO(createUpdateAddressDataDTO);
    }

    @PutMapping("/{id}")
    public AddressDataDTO updateAddressDTO(@RequestBody CreateUpdateAddressDataDTO createUpdateAddressDataDTO, @PathVariable Integer id)
            throws AddressNotFoundException, AddressDataInvalidException, UserNotFoundException {
        return addressDataService.updateAddressDTO(createUpdateAddressDataDTO, id);
    }

    @DeleteMapping("/{id}")
    public AddressDataDTO deleteAddressDTO(@PathVariable Integer id) throws AddressNotFoundException {
        return addressDataService.deleteAddressDTO(id);
    }

    @GetMapping("/user/{id}")
    public List<AddressDataDTO> findAddressesByUserId(@PathVariable Integer id) throws UserNotFoundException {
        return addressDataService.findAddressesByUserId(id);
    }
}
