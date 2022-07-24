package com.teamsfinder.userwriteservice.user.exception;

public class KeycloakException extends RuntimeException {
    private static final String MESSAGE = "Error while disabling user in keycloak server!";

    public KeycloakException() {
        super(MESSAGE);
    }
}
