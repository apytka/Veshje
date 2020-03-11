package com.agatap.veshje.controller;

import com.agatap.veshje.service.exception.UserAlreadyExist;
import com.agatap.veshje.service.exception.UserDataInvalid;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerConfig {

    @ExceptionHandler(UserDataInvalid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleUserBadRequest() {
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
        public void handleUserNotFoundException() {
    }

    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserAlreadyExist() {
    }
}
