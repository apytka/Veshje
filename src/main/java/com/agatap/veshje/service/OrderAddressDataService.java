package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateOrderAddressDataDTO;
import com.agatap.veshje.controller.DTO.OrderAddressDataDTO;
import com.agatap.veshje.controller.mapper.OrderAddressDataMapper;
import com.agatap.veshje.model.AddressData;
import com.agatap.veshje.model.OrderAddressData;
import com.agatap.veshje.model.OrderCheckoutDetails;
import com.agatap.veshje.repository.AddressRepository;
import com.agatap.veshje.repository.OrderAddressDataRepository;
import com.agatap.veshje.service.exception.AddressDataInvalidException;
import com.agatap.veshje.service.exception.AddressNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderAddressDataService {

    private OrderAddressDataRepository orderAddressDataRepository;
    private OrderAddressDataMapper mapper;
    private OrderCheckoutDetailsService orderCheckoutDetailsService;
    private AddressDataService addressDataService;

    public List<OrderAddressDataDTO> getAllOrderAddressData() {
        return orderAddressDataRepository.findAll().stream()
                .map(orderAddressData -> mapper.mappingToDTO(orderAddressData))
                .collect(Collectors.toList());
    }

    public OrderAddressDataDTO findOrderAddressDTOById(Integer id) throws AddressNotFoundException {
        return orderAddressDataRepository.findById(id)
                .map(orderAddressData -> mapper.mappingToDTO(orderAddressData))
                .orElseThrow(() -> new AddressNotFoundException());
    }

    public OrderAddressData findOrderAddressById(Integer id) throws AddressNotFoundException {
        return orderAddressDataRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException());
    }

    public OrderAddressDataDTO createOrderAddressDTO(CreateUpdateOrderAddressDataDTO createUpdateOrderAddressDataDTO) throws AddressDataInvalidException {
        invalidData(createUpdateOrderAddressDataDTO);
        OrderAddressData orderAddressData = mapper.mappingToModel(createUpdateOrderAddressDataDTO);
        orderAddressData.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        OrderAddressData newOrderAddressData = orderAddressDataRepository.save(orderAddressData);
        return mapper.mappingToDTO(newOrderAddressData);
    }

        public OrderAddressData createOrderAddress() throws AddressNotFoundException {
            OrderAddressData orderAddressData = new OrderAddressData();
            OrderCheckoutDetails orderCheckoutDetails = orderCheckoutDetailsService.getOrderCheckoutDetails();
            AddressData addressData = addressDataService.findAddressDataById(orderCheckoutDetails.getAddressId());
            orderAddressData.setFirstName(addressData.getFirstName());
            orderAddressData.setLastName(addressData.getLastName());
            orderAddressData.setStreet(addressData.getStreet());
            orderAddressData.setNo(addressData.getNo());
            orderAddressData.setCity(addressData.getCity());
            orderAddressData.setPostalCode(addressData.getPostalCode());
            orderAddressData.setPhoneNumber(addressData.getPhoneNumber());
            orderAddressData.setCreateDate(OffsetDateTime.now());

            return orderAddressDataRepository.save(orderAddressData);
    }

    public OrderAddressDataDTO updateOrderAddressDTO(CreateUpdateOrderAddressDataDTO createUpdateOrderAddressDataDTO, Integer id)
            throws AddressDataInvalidException, AddressNotFoundException {
        invalidData(createUpdateOrderAddressDataDTO);

        OrderAddressData orderAddressData = findOrderAddressById(id);
        orderAddressData.setFirstName(createUpdateOrderAddressDataDTO.getFirstName());
        orderAddressData.setLastName(createUpdateOrderAddressDataDTO.getLastName());
        orderAddressData.setStreet(createUpdateOrderAddressDataDTO.getStreet());
        orderAddressData.setNo(createUpdateOrderAddressDataDTO.getNo());
        orderAddressData.setPostalCode(createUpdateOrderAddressDataDTO.getPostalCode());
        orderAddressData.setCity(createUpdateOrderAddressDataDTO.getCity());
        orderAddressData.setPhoneNumber(createUpdateOrderAddressDataDTO.getPhoneNumber());
        orderAddressData.setInformation(createUpdateOrderAddressDataDTO.getInformation());
        orderAddressData.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        OrderAddressData updateOrderAddressData = orderAddressDataRepository.save(orderAddressData);
        return mapper.mappingToDTO(updateOrderAddressData);
    }

    public OrderAddressDataDTO deleteOrderAddressDTO(Integer id) throws AddressNotFoundException {
        OrderAddressData orderAddressData = findOrderAddressById(id);
        orderAddressDataRepository.delete(orderAddressData);
        return mapper.mappingToDTO(orderAddressData);
    }

    public OrderAddressDataDTO findOrderAddressDataByOrderId(Integer orderId) throws AddressNotFoundException {
        return orderAddressDataRepository.findByOrderId(orderId)
                .map(orderAddressData -> mapper.mappingToDTO(orderAddressData))
                .orElseThrow(() -> new AddressNotFoundException());
    }

    private void invalidData(CreateUpdateOrderAddressDataDTO createUpdateOrderAddressDataDTO) throws AddressDataInvalidException {
        String postalCodePattern = "^\\d{2}-\\d{3}$";
        String phoneNumberPattern = "^\\+\\d{11}$";
        Pattern patternPostalCode = Pattern.compile(postalCodePattern);
        Matcher matcherPostalCode = patternPostalCode.matcher(createUpdateOrderAddressDataDTO.getPostalCode());
        boolean postalCodeCheck = matcherPostalCode.matches();
        Pattern patternPhoneNumber = Pattern.compile(phoneNumberPattern);
        Matcher matcherPhoneNumber = patternPhoneNumber.matcher(createUpdateOrderAddressDataDTO.getPhoneNumber());
        boolean phoneNumberCheck = matcherPhoneNumber.matches();

        if (!postalCodeCheck || !phoneNumberCheck) {
            throw new AddressDataInvalidException();
        }
    }

}
