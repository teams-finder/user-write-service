package com.teamsfinder.userwriteservice.user.keycloak;

import com.teamsfinder.userwriteservice.user.model.User;

public interface KeycloakService {
    void blockInKeyCloak(User user);
}
