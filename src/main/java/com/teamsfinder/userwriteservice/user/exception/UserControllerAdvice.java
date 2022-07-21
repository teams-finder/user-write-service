package com.teamsfinder.userwriteservice.user.exception;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class UserControllerAdvice {
    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleUserNotFoundException(UserNotFoundException exception){
        return exception.getMessage();
    }

    @ExceptionHandler({KeyCloakException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    String handleKeyCloakException(KeyCloakException exception){
        return exception.getMessage();
    }
}
