package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.controller.DTO.UserDTO;
import com.agatap.veshje.model.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDTOMapper {

    public UserDTO mappingToDTO(User user) {
        //todo - add foreign keys
        Integer newsletterId = Optional.ofNullable(user.getNewsletter())
                .map(newsletter -> user.getId()).orElse(null);
        List<Integer> paymentsId = user.getUserPayments().stream()
                .map(payment -> user.getId())
                .collect(Collectors.toList());
        List<Integer> addressesId = user.getAddresses().stream()
                .map(addresses -> user.getId())
                .collect(Collectors.toList());
        List<Integer> ordersId = user.getOrders().stream()
                .map(order -> user.getId())
                .collect(Collectors.toList());
        List<Integer> reviewsId = user.getReviews().stream()
                .map(reviews -> user.getId())
                .collect(Collectors.toList());
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .newsletterId(newsletterId)
                .paymentIds(paymentsId)
                .addressesIds(addressesId)
                .orderIds(ordersId)
                .reviewsIds(reviewsId)
                .subscribedNewsletter(user.getSubscribedNewsletter())
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .build();
    }

    public User mappingToModel(CreateUserDTO createUserDTO) {
        return User.builder()
                .password(createUserDTO.getPassword())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .email(createUserDTO.getEmail())
                .subscribedNewsletter(createUserDTO.getSubscribedNewsletter())
                .build();
    }
}
