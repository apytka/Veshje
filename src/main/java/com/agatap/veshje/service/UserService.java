package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.NewsletterDTOMapper;
import com.agatap.veshje.controller.mapper.UserDTOMapper;
import com.agatap.veshje.model.Newsletter;
import com.agatap.veshje.model.User;
import com.agatap.veshje.model.UserRole;
import com.agatap.veshje.repository.NewsletterRepository;
import com.agatap.veshje.repository.UserRepository;
import com.agatap.veshje.service.exception.NewsletterNotFoundException;
import com.agatap.veshje.service.exception.UserAlreadyExistException;
import com.agatap.veshje.service.exception.UserDataInvalidException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapper mapper;
    @Autowired
    private NewsletterDTOMapper newsletterDTOMapper;
    @Autowired
    private NewsletterRepository newsletterRepository;

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

    public UserDTO createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistException, UserDataInvalidException, NewsletterNotFoundException {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserAlreadyExistException();
        }
        if(createUserDTO.getEmail() == null || createUserDTO.getPassword() == null || createUserDTO.getPassword().length() < 3
        || createUserDTO.getConfirmPassword() == null || createUserDTO.getConfirmPassword().length() < 3) {
            throw new UserDataInvalidException();
        }

        if(!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            throw new UserDataInvalidException();
        }

        User user = mapper.mappingToModel(createUserDTO);
        user.setUserRole(UserRole.USER);
        user.setSubscribedNewsletter(false);
        user.setCreateDate(OffsetDateTime.now());

        if(createUserDTO.getSubscribedNewsletter()) {
            Newsletter newsletter = addNewsletterForUser(user);
            user.setNewsletter(newsletter);
            newsletter.getUsers().add(user);
        }

        User newUser = userRepository.save(user);
        return mapper.mappingToDTO(newUser);

    }

    private Newsletter addNewsletterForUser(User user) {
        CreateUpdateNewsletterDTO createUpdateNewsletterDTO = new CreateUpdateNewsletterDTO();
        Newsletter newsletter = newsletterDTOMapper.mappingToModel(createUpdateNewsletterDTO);
        newsletter.setEmail(user.getEmail());
        newsletter.setCreateDate(OffsetDateTime.now());
        newsletterRepository.save(newsletter);
        return newsletter;
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO, Integer id) throws UserNotFoundException {
        User user = findUserById(id);
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setEmail(updateUserDTO.getEmail());
        user.setUserRole(updateUserDTO.getUserRole());
        user.setSubscribedNewsletter(updateUserDTO.getSubscribedNewsletter());
        user.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        User updateUser = userRepository.save(user);
        return mapper.mappingToDTO(updateUser);
    }

    @Transactional
    public UserDTO deleteUser(Integer id) throws UserNotFoundException {
        User user = findUserById(id);
        userRepository.delete(user);
        return mapper.mappingToDTO(user);
    }
}
