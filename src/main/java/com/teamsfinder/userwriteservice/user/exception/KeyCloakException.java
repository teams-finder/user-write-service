package com.teamsfinder.userwriteservice.user.exception;

public class KeyCloakException extends RuntimeException {
    private static final String MESSAGE = "Error while disabling user in keycloak server!";

    public KeyCloakException() {
        super(MESSAGE);
    }
}
