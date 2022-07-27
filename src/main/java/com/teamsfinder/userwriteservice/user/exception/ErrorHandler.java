package com.teamsfinder.userwriteservice.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
class ErrorHandler {

    @ExceptionHandler({KeycloakException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleKeyCloakException(KeycloakException exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleUserNotFoundException(UserNotFoundException exception) {
        return exception.getMessage();
    }
}
