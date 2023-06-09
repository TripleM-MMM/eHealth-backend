package com.mstrzezon.userservice.controller;

import com.mstrzezon.userservice.dto.*;
import com.mstrzezon.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
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
    public UserOutDTO getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDTO createUser(@RequestBody UserInDTO userInDTO) {
        return userService.createUser(userInDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutDTO updateUser(@PathVariable String id, @RequestBody UpdatedUserDTO updatedUserDTO) {
        return userService.updateUser(id, updatedUserDTO);
    }

    @PostMapping("/{id}/credentials/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable String id, @RequestBody String password) {
        userService.changePassword(id, password);
    }

    @PostMapping("/{id}/credentials/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@PathVariable String id) {
        userService.forgotPassword(id);
    }

    @PostMapping("/{user_id}/devices")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutDTO addDevice(@PathVariable("user_id") Long userId, @RequestBody DeviceInDTO deviceInDTO) {
        return userService.addDevice(userId, deviceInDTO);
    }


    @PostMapping("/access-token")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse getAccessToken(@RequestBody AccessTokenDTO accessTokenDTO) {
        return userService.getAccessToken(accessTokenDTO);
    }
}
