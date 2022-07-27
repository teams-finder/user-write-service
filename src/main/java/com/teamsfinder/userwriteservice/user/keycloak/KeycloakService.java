package com.teamsfinder.userwriteservice.user.keycloak;

import com.teamsfinder.userwriteservice.user.exception.KeycloakException;
import com.teamsfinder.userwriteservice.user.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    @Value("keycloak.auth-url")
    private String KEYCLOAK_AUTH_URL;
    @Value("keycloak.realm")
    private String KEYCLOAK_REALM;
    @Value("keycloak.master-realm")
    private String KEYCLOAK_MASTER_REALM;
    @Value("keycloak.client-id")
    private String KEYCLOAK_CLIENT_ID;
    @Value("keycloak.username")
    private String KEYCLOAK_USERNAME;
    @Value("keycloak.password")
    private String KEYCLOAK_PASSWORD;

    public void blockInKeyCloak(User user) {
        try {
            Keycloak keycloak = buildKeyCloak();
            UserResource userResource = getUserResource(user.getKeyCloakId(),
                    keycloak);
            UserRepresentation userRepresentation =
                    userResource.toRepresentation();
            userRepresentation.setEnabled(false);
            userResource.update(userRepresentation);
        } catch (Exception exception) {
            throw new KeycloakException(exception.getMessage());
        }
    }

    private UserResource getUserResource(String keyCloakId, Keycloak keycloak) {
        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keyCloakId);
        return userResource;
    }

    private Keycloak buildKeyCloak() {
        return KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_AUTH_URL)
                .realm(KEYCLOAK_MASTER_REALM)
                .clientId(KEYCLOAK_CLIENT_ID)
                .username(KEYCLOAK_USERNAME)
                .password(KEYCLOAK_PASSWORD)
                .build();
    }
}
