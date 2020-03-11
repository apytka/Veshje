package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUserDTO;
import com.agatap.veshje.controller.DTO.UpdateUserDTO;
import com.agatap.veshje.controller.DTO.UserDTO;
import com.agatap.veshje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public UserDTO finsUserDTOById(@PathVariable Integer id) {
        return userService.findUserDTOById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable Integer id) {
        return userService.updateUser(updateUserDTO, id);
    }

    @DeleteMapping("/{id}")
    public UserDTO deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
