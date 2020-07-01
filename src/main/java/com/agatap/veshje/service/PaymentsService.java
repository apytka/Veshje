package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdatePaymentsDTO;
import com.agatap.veshje.controller.DTO.PaymentsDTO;
import com.agatap.veshje.controller.mapper.PaymentsDTOMapper;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.PaymentsRepository;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentsService {

    private PaymentsRepository paymentsRepository;
    private PaymentsDTOMapper mapper;
    private OrderCheckoutDetailsService orderCheckoutDetailsService;
    private ShoppingCartService shoppingCartService;
    private PaymentsTypeService paymentsTypeService;
    private UserService userService;

    public List<PaymentsDTO> getAllPayments() {
        return paymentsRepository.findAll().stream()
                .map(payments -> mapper.mappingToDTO(payments))
                .collect(Collectors.toList());
    }

    public PaymentsDTO findPaymentsDTOById(Integer id) throws PaymentsNotFoundException {
        return paymentsRepository.findById(id)
                .map(payments -> mapper.mappingToDTO(payments))
                .orElseThrow(() -> new PaymentsNotFoundException());
    }

    public Payments findPaymentsById(Integer id) throws PaymentsNotFoundException {
        return paymentsRepository.findById(id)
                .orElseThrow(() -> new PaymentsNotFoundException());
    }

    public PaymentsDTO createPaymentsDTO(CreateUpdatePaymentsDTO createPaymentsDTO) throws PaymentsDataInvalidException {
        if (createPaymentsDTO.getAmount().doubleValue() <= 0) {
            throw new PaymentsDataInvalidException();
        }
        Payments payments = mapper.mappingToModel(createPaymentsDTO);
        payments.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Payments newPayments = paymentsRepository.save(payments);
        return mapper.mappingToDTO(newPayments);
    }

    public Payments createPayments(Integer userId)
            throws DeliveryNotFoundException, ProductNotFoundException, PaymentsDataInvalidException, PaymentsTypeNotFoundException, UserNotFoundException {
        OrderCheckoutDetails orderCheckoutDetails = orderCheckoutDetailsService.getOrderCheckoutDetails();
        Integer paymentTypeId = orderCheckoutDetails.getPaymentId();
        Integer deliveryId = orderCheckoutDetails.getDeliveryId();
        Double totalPriceWithDelivery = shoppingCartService.getTotalPriceWithDelivery(deliveryId);
        if (totalPriceWithDelivery <= 0) {
            throw new PaymentsDataInvalidException();
        }
        Payments payment = new Payments();
        payment.setAmount(BigDecimal.valueOf(totalPriceWithDelivery));
        payment.setPaymentStatus(PaymentsStatus.PENDING);
        payment.setCreateDate(OffsetDateTime.now());

        PaymentsType paymentsTypeById = paymentsTypeService.findPaymentsTypeById(paymentTypeId);
        payment.setTypePayment(paymentsTypeById);
        paymentsTypeById.getPayments().add(payment);

        User user = userService.findUserById(userId);
        user.getUserPayments().add(payment);
        payment.setUsers(user);

        return paymentsRepository.save(payment);
    }

    public PaymentsDTO updatePaymentsDTO(CreateUpdatePaymentsDTO updatePaymentsDTO, Integer id)
            throws PaymentsNotFoundException {
        Payments payments = findPaymentsById(id);
        payments.setAmount(updatePaymentsDTO.getAmount());
        payments.setPaymentStatus(updatePaymentsDTO.getPaymentStatus());
        payments.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Payments updatePayments = paymentsRepository.save(payments);
        return mapper.mappingToDTO(updatePayments);
    }

    public PaymentsDTO deletePaymentsDTO(Integer id) throws PaymentsNotFoundException {
        Payments payments = findPaymentsById(id);
        paymentsRepository.delete(payments);
        return mapper.mappingToDTO(payments);
    }
}

