package com.mstrzezon.userservice.service;

import com.mstrzezon.userservice.client.KeycloakClient;
import com.mstrzezon.userservice.dto.*;
import com.mstrzezon.userservice.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static com.mstrzezon.userservice.utils.EmailValidator.isValid;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakClient keycloakClient;
    private final WebClient.Builder webClientBuilder;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserOutDTO> getUsers() {
        return keycloakClient.getUsersResource().list().stream()
                .map(userRepresentation -> UserOutDTO.builder()
                        .id(userRepresentation.getId())
                        .firstName(userRepresentation.getFirstName())
                        .lastName(userRepresentation.getLastName())
                        .username(userRepresentation.getUsername())
                        .email(userRepresentation.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public UserOutDTO getUser(String id) {
        try {
            UserRepresentation user = keycloakClient.getUsersResource().get(id).toRepresentation();
            return UserOutDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
        } catch (NotFoundException notFoundException) {
            logger.error(notFoundException.getMessage());
            throw new ResponseStatusException(NOT_FOUND, "User not found");
        }
    }

    public UserOutDTO createUser(UserInDTO userInDTO) {
        validateData(userInDTO);
        Response response = keycloakClient.getUsersResource().create(UserUtils.mapUserInDTOToUserRepresentation(userInDTO));
        logger.info("Response |  Status: {} | Status Info: {}", response.getStatus(), response.getStatusInfo());
        if (response.getStatus() != 201) {
            throw new RuntimeException("User not created");
        }
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        UserResource userResource = keycloakClient.getUsersResource().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        return UserOutDTO.builder()
                .id(userRepresentation.getId())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .build();
    }

    private void validateData(UserInDTO userInDTO) {
        if (!keycloakClient.getUsersResource().searchByEmail(userInDTO.getEmail(), true).isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "User with email already exists");
        }
        if (!keycloakClient.getUsersResource().searchByUsername(userInDTO.getUsername(), true).isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "User with username already exists");
        }
    }

    public UserOutDTO updateUser(String id, UpdatedUserDTO updatedUserDTO) {
        if (!isValid(updatedUserDTO.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "Email is not valid");
        }
        try {
            UserResource userResource = keycloakClient.getUsersResource().get(id);
            UserRepresentation userRepresentation = userResource.toRepresentation();
            updateUser(userRepresentation, updatedUserDTO);
            userResource.update(userRepresentation);
            return UserOutDTO.builder()
                    .id(userRepresentation.getId())
                    .firstName(userRepresentation.getFirstName())
                    .lastName(userRepresentation.getLastName())
                    .username(userRepresentation.getUsername())
                    .email(userRepresentation.getEmail())
                    .build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(NOT_FOUND, "User not found");
        } catch (BadRequestException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    public void deleteUser(String id) {
        try {
            keycloakClient.getUsersResource().get(id).remove();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(NOT_FOUND, "User not found");
        }
    }

    public void changePassword(String id, String password) {
        keycloakClient.getUsersResource().get(id).resetPassword(UserUtils.mapPasswordToCredentialRepresentation(password));
    }

    public DeviceOutDTO addDevice(long userId, DeviceInDTO deviceInDTO) {
        DeviceOutDTO device = webClientBuilder.build().post().uri("http://device-service/api/device")
                .body(BodyInserters.fromValue(deviceInDTO))
                .retrieve().bodyToMono(DeviceOutDTO.class).block();
        return webClientBuilder.build().post().uri("http://device-service/api/device/" + device.getId() + "/user/" + userId)
                .retrieve().bodyToMono(DeviceOutDTO.class).block();
    }

    public void forgotPassword(String id) {
        throw new NotImplementedException();
    }

    public AccessTokenResponse getAccessToken(AccessTokenDTO accessTokenDTO) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081")
                .realm("eHealth")
                .clientId("spring-boot-client")
                .username(accessTokenDTO.getUsername())
                .password(accessTokenDTO.getPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
        return keycloak.tokenManager().getAccessToken();
    }

    private void updateUser(UserRepresentation userRepresentation, UpdatedUserDTO updatedUserDTO) {
        userRepresentation.setFirstName(updatedUserDTO.getFirstName());
        userRepresentation.setLastName(updatedUserDTO.getLastName());
        userRepresentation.setEmail(updatedUserDTO.getEmail());
        keycloakClient.getUsersResource().get(userRepresentation.getId()).update(userRepresentation);
    }

}
