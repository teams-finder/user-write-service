package com.teamsfinder.userwriteservice.user.exception.handler;

import com.teamsfinder.userwriteservice.user.exception.KeycloakException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class KeycloakErrorHandler {

    @ExceptionHandler({KeycloakException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    String handleKeyCloakException(KeycloakException exception) {
        return exception.getMessage();
    }
}
