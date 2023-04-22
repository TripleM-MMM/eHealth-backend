package com.mstrzezon.userservice.service;

import com.mstrzezon.userservice.client.KeycloakClient;
import com.mstrzezon.userservice.dto.UserInDTO;
import com.mstrzezon.userservice.dto.UserOutDTO;
import com.mstrzezon.userservice.model.User;
import com.mstrzezon.userservice.repository.UserRepository;
import com.mstrzezon.userservice.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final KeycloakClient keycloakClient;

    public List<UserOutDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserOutDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public UserOutDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserOutDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public UserOutDTO createUser(UserInDTO userInDTO) {
        keycloakClient.getUsersResource().create(UserUtils.mapUserInDTOToUserRepresentation(userInDTO));
        return saveToDatabase(userInDTO);
    }

    public UserOutDTO updateUser(Long id, UserInDTO userInDTO) {
        keycloakClient.getUsersResource().get(id.toString()).update(UserUtils.mapUserInDTOToUserRepresentation(userInDTO));
        return saveToDatabase(id, userInDTO);
    }

    public void changePassword(Long id, String password) {
        throw new NotImplementedException();
    }

    public void forgotPassword(Long id) {
        throw new NotImplementedException();
    }

    private UserOutDTO saveToDatabase(UserInDTO userInDTO) {
        User user = new User();
        user.setFirstName(userInDTO.getFirstName());
        user.setLastName(userInDTO.getLastName());
        user.setUsername(userInDTO.getUsername());
        user.setEmail(userInDTO.getEmail());
        user.setPassword(userInDTO.getPassword());
        User savedUser = userRepository.save(user);
        return UserOutDTO.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    private UserOutDTO saveToDatabase(Long id, UserInDTO userInDTO) {
        User user = new User();
        user.setId(id);
        user.setFirstName(userInDTO.getFirstName());
        user.setLastName(userInDTO.getLastName());
        user.setUsername(userInDTO.getUsername());
        user.setEmail(userInDTO.getEmail());
        user.setPassword(userInDTO.getPassword());
        User savedUser = userRepository.save(user);
        return UserOutDTO.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }
}
