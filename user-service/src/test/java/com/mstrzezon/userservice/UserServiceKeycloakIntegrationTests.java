package com.mstrzezon.userservice;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class UserServiceKeycloakIntegrationTests {

    @Container
    static KeycloakContainer keycloakContainer = new KeycloakContainer()
            .withRealmImportFile("./realm-export.json");

    @Test
    public void shouldReturnUsers() {
        String authServerUrl = keycloakContainer.getAuthServerUrl();
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();

        assertTrue(keycloak.realm("eHealth").users().count() > 0);
    }

    @Test
    public void shouldReturnAccessToken() {
        String authServerUrl = keycloakContainer.getAuthServerUrl();
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm("eHealth")
                .clientId("spring-boot-client")
                .username("mretajczyk")
                .password("mretajczyk")
                .build();

        assertNotNull(keycloak.tokenManager().getAccessToken().getToken());
    }
}
