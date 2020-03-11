package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.controller.DTO.UserDTO;
import com.agatap.veshje.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserDTO mappingToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .build();
    }

    public User mappingToModel(CreateUserDTO createUserDTO) {
        return User.builder()
                .login(createUserDTO.getLogin())
                .password(createUserDTO.getPassword())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .email(createUserDTO.getEmail())
                .build();
    }
}
