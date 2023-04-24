package com.mstrzezon.userservice.controller;

import com.mstrzezon.userservice.dto.UpdatedUserDTO;
import com.mstrzezon.userservice.dto.UserInDTO;
import com.mstrzezon.userservice.dto.UserOutDTO;
import com.mstrzezon.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserOutDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutDTO getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDTO createUser(@RequestBody UserInDTO userInDTO) {
        return userService.createUser(userInDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutDTO updateUser(@PathVariable Long id, @RequestBody UpdatedUserDTO updatedUserDTO) {
        return userService.updateUser(id, updatedUserDTO);
    }

    @PostMapping("/{id}/credentials/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable Long id, @RequestBody String password) {
        userService.changePassword(id, password);
    }

    @PostMapping("/{id}/credentials/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@PathVariable Long id) {
        userService.forgotPassword(id);
    }
}
