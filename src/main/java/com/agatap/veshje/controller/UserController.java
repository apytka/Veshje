package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.controller.DTO.TokenDTO;
import com.agatap.veshje.controller.DTO.UpdateUserDTO;
import com.agatap.veshje.controller.DTO.UserDTO;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO finsUserDTOById(@PathVariable Integer id) throws UserNotFoundException {
        return userService.findUserDTOById(id);
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws UserDataInvalidException, UserAlreadyExistException, NewsletterNotFoundException, NewsletterDataInvalidException, NewsletterAlreadyExistsException, AddressDataInvalidException {
        return userService.createUser(createUserDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO, @PathVariable Integer id) throws UserNotFoundException, NewsletterAlreadyExistsException, NewsletterNotFoundException {
        return userService.updateUser(updateUserDTO, id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public UserDTO deleteUser(@PathVariable Integer id) throws UserNotFoundException {
        return userService.deleteUser(id);
    }

    @GetMapping("/token/{id}")
    public TokenDTO getTokenByUserId(@PathVariable Integer id) throws UserNotFoundException {
        return userService.getTokenByUserId(id);
    }
}
