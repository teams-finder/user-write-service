package com.teamsfinder.userwriteservice.user.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User with id %d not found in base!";

    public UserNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
