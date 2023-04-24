package com.mstrzezon.userservice.utils;

import com.mstrzezon.userservice.dto.UserInDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;

public class UserUtils {

    public static String getUserIdFromLocationHeader(javax.ws.rs.core.Response response) {
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    public static UserRepresentation mapUserInDTOToUserRepresentation(UserInDTO userInDTO) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userInDTO.getFirstName());
        userRepresentation.setLastName(userInDTO.getLastName());
        userRepresentation.setUsername(userInDTO.getUsername());
        userRepresentation.setEmail(userInDTO.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Collections.singletonList(mapPasswordToCredentialRepresentation(userInDTO.getPassword())));
        return userRepresentation;
    }

    public static CredentialRepresentation mapPasswordToCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }
}
