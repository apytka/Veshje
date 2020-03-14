package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.AddressDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateAddressDTO;
import com.agatap.veshje.controller.mapper.AddressDTOMapper;
import com.agatap.veshje.model.Address;
import com.agatap.veshje.repository.AddressRepository;
import com.agatap.veshje.service.exception.AddressDataInvalidException;
import com.agatap.veshje.service.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressDTOMapper mapper;

    public List<AddressDTO> getAllAddress() {
        return addressRepository.findAll().stream()
                .map(address -> mapper.mappingToDTO(address))
                .collect(Collectors.toList());
    }

    public AddressDTO findAddressDTOById(Integer id) throws AddressNotFoundException {
        return addressRepository.findById(id)
                .map(address -> mapper.mappingToDTO(address))
                .orElseThrow(() -> new AddressNotFoundException());
    }

    public Address findAddressOById(Integer id) throws AddressNotFoundException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException());
    }

    public AddressDTO createAddressDTO(CreateUpdateAddressDTO createAddressDTO)
            throws AddressDataInvalidException {
        invalidData(createAddressDTO);
        Address address = mapper.mappingToModel(createAddressDTO);
        address.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Address newAddress = addressRepository.save(address);
        return mapper.mappingToDTO(newAddress);
    }

    public AddressDTO updateAddressDTO(CreateUpdateAddressDTO updateAddressDTO, Integer id) throws AddressNotFoundException, AddressDataInvalidException {
        invalidData(updateAddressDTO);
        Address address = findAddressOById(id);
        address.setStreet(updateAddressDTO.getStreet());
        address.setNo(updateAddressDTO.getNo());
        address.setPostalCode(updateAddressDTO.getPostalCode());
        address.setPhoneNumber(updateAddressDTO.getPhoneNumber());
        address.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Address updateAddress = addressRepository.save(address);
        return mapper.mappingToDTO(updateAddress);
    }

    public AddressDTO deleteAddressDTO(Integer id) throws AddressNotFoundException {
        Address address = findAddressOById(id);
        addressRepository.delete(address);
        return mapper.mappingToDTO(address);
    }

    private void invalidData(CreateUpdateAddressDTO createUpdateAddressDTO) throws AddressDataInvalidException {
        String postalCodePattern = "\\d{2}-\\d{3}";
        String phoneNumberPattern = "\\+\\d{11}";
        Pattern patternPostalCode = Pattern.compile(postalCodePattern);
        Matcher matcherPostalCode = patternPostalCode.matcher(createUpdateAddressDTO.getPostalCode());
        boolean postalCodeCheck = matcherPostalCode.matches();
        Pattern patternPhoneNumber = Pattern.compile(phoneNumberPattern);
        Matcher matcherPhoneNumber = patternPhoneNumber.matcher(createUpdateAddressDTO.getPhoneNumber());
        boolean phoneNumberCheck = matcherPhoneNumber.matches();

        if (!postalCodeCheck || !phoneNumberCheck) {
            throw new AddressDataInvalidException();
        }
    }
}