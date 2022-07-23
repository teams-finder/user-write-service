package com.teamsfinder.userwriteservice.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class KeyCloakHandler {
    @ExceptionHandler({KeyCloakException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    String handleKeyCloakException(KeyCloakException exception){
        return exception.getMessage();
    }
}
