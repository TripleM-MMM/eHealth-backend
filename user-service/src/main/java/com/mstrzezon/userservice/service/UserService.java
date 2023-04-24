package com.mstrzezon.userservice.service;

import com.mstrzezon.userservice.client.KeycloakClient;
import com.mstrzezon.userservice.dto.UpdatedUserDTO;
import com.mstrzezon.userservice.dto.UserInDTO;
import com.mstrzezon.userservice.dto.UserOutDTO;
import com.mstrzezon.userservice.model.User;
import com.mstrzezon.userservice.repository.UserRepository;
import com.mstrzezon.userservice.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
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
                        .keycloakUserId(user.getKeycloakUserId())
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
        Response response = keycloakClient.getUsersResource().create(UserUtils.mapUserInDTOToUserRepresentation(userInDTO));
        if (response.getStatus() != 201) {
            throw new RuntimeException("Error creating user");
        }
        String keycloakUserId = UserUtils.getUserIdFromLocationHeader(response);
        return saveToDatabase(userInDTO, keycloakUserId);
    }

    public UserOutDTO updateUser(Long id, UpdatedUserDTO updatedUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String keycloakUserId = user.getKeycloakUserId();
        UserRepresentation userRepresentation = keycloakClient.getUsersResource().get(keycloakUserId).toRepresentation();
        try {
            updateUser(userRepresentation, updatedUserDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user in keycloak");
        }
        return saveToDatabase(user, updatedUserDTO);
    }

    public void changePassword(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String keycloakUserId = user.getKeycloakUserId();
        UserRepresentation userRepresentation = keycloakClient.getUsersResource().get(keycloakUserId).toRepresentation();
        CredentialRepresentation credentialRepresentation = UserUtils.mapPasswordToCredentialRepresentation(password);
        keycloakClient.getUsersResource().get(keycloakUserId).resetPassword(credentialRepresentation);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void forgotPassword(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String keycloakUserId = user.getKeycloakUserId();
        UserRepresentation userRepresentation = keycloakClient.getUsersResource().get(keycloakUserId).toRepresentation();
        keycloakClient.getUsersResource().get(keycloakUserId).executeActionsEmail(List.of("RESET_PASSWORD"));
    }

    private void updateUser(UserRepresentation userRepresentation, UpdatedUserDTO updatedUserDTO) {
        userRepresentation.setFirstName(updatedUserDTO.getFirstName());
        userRepresentation.setLastName(updatedUserDTO.getLastName());
        userRepresentation.setEmail(updatedUserDTO.getEmail());
        keycloakClient.getUsersResource().get(userRepresentation.getId()).update(userRepresentation);
    }

    private UserOutDTO saveToDatabase(UserInDTO userInDTO, String keycloakUserId) {
        User user = new User();
        user.setKeycloakUserId(keycloakUserId);
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
                .keycloakUserId(savedUser.getKeycloakUserId())
                .build();
    }

    private UserOutDTO saveToDatabase(User user, UpdatedUserDTO updatedUserDTO) {
        user.setFirstName(updatedUserDTO.getFirstName());
        user.setLastName(updatedUserDTO.getLastName());
        user.setEmail(updatedUserDTO.getEmail());
        User savedUser = userRepository.save(user);
        return UserOutDTO.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .keycloakUserId(savedUser.getKeycloakUserId())
                .build();
    }
}
