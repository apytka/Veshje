package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.controller.DTO.UpdateUserDTO;
import com.agatap.veshje.controller.DTO.UserDTO;
import com.agatap.veshje.controller.mapper.UserDTOMapper;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.UserRole;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.service.exception.UserAlreadyExistException;
import com.agatap.veshje.service.exception.UserDataInvalidException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapper mapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapper.mappingToDTO(user))
                .collect(Collectors.toList());
    }

    public UserDTO findUserDTOById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> mapper.mappingToDTO(user))
                .orElseThrow(() -> new UserNotFoundException());
    }

    public User findUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistException, UserDataInvalidException {
        if (userRepository.existsByLogin(createUserDTO.getLogin())) {
            throw new UserAlreadyExistException();
        }
        if(createUserDTO.getLogin() == null || createUserDTO.getLogin().length() < 3 ||
                createUserDTO.getPassword() == null || createUserDTO.getPassword().length() < 3) {
            throw new UserDataInvalidException();
        }

        User user = mapper.mappingToModel(createUserDTO);
        user.setUserRole(UserRole.USER);
        user.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        User newUser = userRepository.save(user);
        return mapper.mappingToDTO(newUser);

    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO, Integer id) throws UserNotFoundException {
        User user = findUserById(id);
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setEmail(updateUserDTO.getEmail());
        updateUserDTO.setBirthDate(updateUserDTO.getBirthDate());
        user.setUserRole(updateUserDTO.getUserRole());
        user.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        User updateUser = userRepository.save(user);
        return mapper.mappingToDTO(updateUser);
    }

    public UserDTO deleteUser(Integer id) throws UserNotFoundException {
        User user = findUserById(id);
        userRepository.delete(user);
        return mapper.mappingToDTO(user);
    }
}
