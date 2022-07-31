package com.teamsfinder.userwriteservice.user;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class KeycloakIntegrationBaseClass extends IntegrationBaseClass {

    private static final KeycloakContainer keycloakContainer;

    static {
        keycloakContainer = new KeycloakContainer(
                "quay" +
                        ".io/keycloak/keycloak:16.0.0").withRealmImportFile(
                "/realm" +
                        "-export.json");
        keycloakContainer.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        IntegrationBaseClass.setDatasourceProperties(registry);
        registry.add("keycloak.authUrl", keycloakContainer::getAuthServerUrl);
        registry.add("keycloak.username", keycloakContainer::getAdminUsername);
        registry.add("keycloak.password", keycloakContainer::getAdminPassword);
    }
}
