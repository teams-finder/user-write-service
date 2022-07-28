package com.teamsfinder.userwriteservice.user.keycloak;

import com.teamsfinder.userwriteservice.user.exception.KeycloakException;
import com.teamsfinder.userwriteservice.user.model.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakProperties keycloakProperties;

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
        RealmResource realmResource =
                keycloak.realm(keycloakProperties.getRealm());
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keyCloakId);
        return userResource;
    }

    private Keycloak buildKeyCloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getAuthUrl())
                .realm(keycloakProperties.getMasterRealm())
                .clientId(keycloakProperties.getClientId())
                .username(keycloakProperties.getUsername())
                .password(keycloakProperties.getPassword())
                .build();
    }
}
