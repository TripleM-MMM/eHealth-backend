package com.mstrzezon.userservice.client;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClient {

    public UsersResource getUsersResource() {
        return getInstance().realm("eHealth").users();
    }

    public RolesResource getRolesResource() {
        return getInstance().realm("eHealth").roles();
    }

    public TokenManager getTokenManager() {
        return getInstance().tokenManager();
    }

    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081")
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();
    }
}
