package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.AddressDataDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateAddressDataDTO;
import com.agatap.veshje.controller.mapper.AddressDataDTOMapper;
import com.agatap.veshje.model.AddressData;
import com.agatap.veshje.model.User;
import com.agatap.veshje.repository.AddressDataRepository;
import com.agatap.veshje.service.exception.AddressDataInvalidException;
import com.agatap.veshje.service.exception.AddressNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressDataService {

    private AddressDataRepository addressDataRepository;
    private AddressDataDTOMapper mapper;
    private UserService userService;

    public List<AddressDataDTO> getAllAddressData() {
        return addressDataRepository.findAll().stream()
                .map(address -> mapper.mappingToDTO(address))
                .collect(Collectors.toList());
    }

    public AddressDataDTO findAddressDataDTOById(Integer id) throws AddressNotFoundException {
        return addressDataRepository.findById(id)
                .map(address -> mapper.mappingToDTO(address))
                .orElseThrow(() -> new AddressNotFoundException());
    }

    public AddressData findAddressDataById(Integer id) throws AddressNotFoundException {
        return addressDataRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException());
    }

    public AddressDataDTO createAddressDTO(CreateUpdateAddressDataDTO createUpdateAddressDataDTO) throws AddressDataInvalidException, UserNotFoundException {
        invalidData(createUpdateAddressDataDTO);

        AddressData addressData = mapper.mappingToModel(createUpdateAddressDataDTO);
        addressData.setCreateDate(OffsetDateTime.now());
        if(createUpdateAddressDataDTO.getUserId() != null) {
            User user = userService.findUserById(createUpdateAddressDataDTO.getUserId());
            addressData.setUser(user);
            user.getAddressData().add(addressData);
        }
        AddressData saveAddressData = addressDataRepository.save(addressData);
        return mapper.mappingToDTO(saveAddressData);
    }

    public AddressDataDTO updateAddressDTO(CreateUpdateAddressDataDTO createUpdateAddressDataDTO, Integer id)
            throws AddressNotFoundException, UserNotFoundException, AddressDataInvalidException {
        invalidData(createUpdateAddressDataDTO);

        AddressData addressData = findAddressDataById(id);
        addressData.setFirstName(createUpdateAddressDataDTO.getFirstName());
        addressData.setLastName(createUpdateAddressDataDTO.getLastName());
        addressData.setStreet(createUpdateAddressDataDTO.getStreet());
        addressData.setNo(createUpdateAddressDataDTO.getNo());
        addressData.setPostalCode(createUpdateAddressDataDTO.getPostalCode());
        addressData.setCity(createUpdateAddressDataDTO.getCity());
        addressData.setPhoneNumber(createUpdateAddressDataDTO.getPhoneNumber());
        addressData.setInformation(createUpdateAddressDataDTO.getInformation());
        addressData.setUpdateDate(OffsetDateTime.now());

        if(createUpdateAddressDataDTO.getUserId() != null) {
            User user = userService.findUserById(createUpdateAddressDataDTO.getUserId());
            addressData.setUser(user);
            user.getAddressData().add(addressData);
        }

        AddressData updateAddressData = addressDataRepository.save(addressData);
        return mapper.mappingToDTO(updateAddressData);
    }

    public AddressDataDTO deleteAddressDTO(Integer id) throws AddressNotFoundException {
        AddressData addressData = findAddressDataById(id);
        addressDataRepository.delete(addressData);
        return mapper.mappingToDTO(addressData);
    }

    private void invalidData(CreateUpdateAddressDataDTO createUpdateAddressDataDTO) throws AddressDataInvalidException {
        String postalCodePattern = "\\d{2}-\\d{3}";
        String phoneNumberPattern = "\\+\\d{11}";
        Pattern patternPostalCode = Pattern.compile(postalCodePattern);
        Matcher matcherPostalCode = patternPostalCode.matcher(createUpdateAddressDataDTO.getPostalCode());
        boolean postalCodeCheck = matcherPostalCode.matches();
        Pattern patternPhoneNumber = Pattern.compile(phoneNumberPattern);
        Matcher matcherPhoneNumber = patternPhoneNumber.matcher(createUpdateAddressDataDTO.getPhoneNumber());
        boolean phoneNumberCheck = matcherPhoneNumber.matches();

        if (!postalCodeCheck || !phoneNumberCheck) {
            throw new AddressDataInvalidException();
        }
    }

    public List<AddressDataDTO> findAddressesByUserId(Integer id) throws UserNotFoundException {
        User user = userService.findUserById(id);
        return user.getAddressData().stream()
                .map(addressData -> mapper.mappingToDTO(addressData))
                .collect(Collectors.toList());
    }
}
