package com.agatap.veshje.controller;

import com.agatap.veshje.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerConfig {

    @ExceptionHandler(UserDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleUserBadRequest() {
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
        public void handleUserNotFoundException() {
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserAlreadyExist() {
    }

    @ExceptionHandler(StoreDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleStoreBadRequest() {
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleStoreNotFoundException() {
    }

    @ExceptionHandler(ReviewDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleReviewBadRequest() {
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleReviewNotFoundException() {
    }

    @ExceptionHandler(ProductDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleProductBadRequest() {
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleProductNotFoundException() {
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleProductAlreadyExist() {
    }

    @ExceptionHandler(DimensionDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDimensionBadRequest() {
    }

    @ExceptionHandler(DimensionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDimensionNotFoundException() {
    }

    @ExceptionHandler(DimensionAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDimensionAlreadyExists() {
    }
}
