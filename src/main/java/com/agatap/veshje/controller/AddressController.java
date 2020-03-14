package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.AddressDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateAddressDTO;
import com.agatap.veshje.service.AddressService;
import com.agatap.veshje.service.exception.AddressDataInvalidException;
import com.agatap.veshje.service.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<AddressDTO> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/{id}")
    public AddressDTO findAddressDTOById(@PathVariable Integer id) throws AddressNotFoundException {
        return addressService.findAddressDTOById(id);
    }

    @PostMapping
    public AddressDTO createAddressDTO(@RequestBody CreateUpdateAddressDTO createAddressDTO)
            throws AddressDataInvalidException {
        return addressService.createAddressDTO(createAddressDTO);
    }

    @PutMapping("/{id}")
    public AddressDTO updateAddressDTO(@RequestBody CreateUpdateAddressDTO updateAddressDTO, @PathVariable Integer id)
            throws AddressNotFoundException, AddressDataInvalidException {
        return addressService.updateAddressDTO(updateAddressDTO, id);
    }

    @DeleteMapping("/{id}")
    public AddressDTO deleteAddressDTO(@PathVariable Integer id) throws AddressNotFoundException {
        return addressService.deleteAddressDTO(id);
    }
}
