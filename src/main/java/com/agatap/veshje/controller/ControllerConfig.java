package com.agatap.veshje.controller;

import com.agatap.veshje.service.exception.*;
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

    @ExceptionHandler(StoreDataInvalid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleStoreBadRequest() {
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleStoreNotFoundException() {
    }

    @ExceptionHandler(ReviewDataInvalid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleReviewBadRequest() {
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleReviewNotFoundException() {
    }
}
