package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = BadIdException.class)
    public ResponseEntity<CustomErrorResponse> handleProductNotFoundException(BadIdException nfe) {
        CustomErrorResponse error = new CustomErrorResponse(nfe.getMessage(), HttpStatus.NOT_FOUND);
        ResponseEntity<CustomErrorResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value= NonMatchingIdException.class)
    public ResponseEntity<CustomErrorResponse> handleNonMatchingException(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        ResponseEntity<CustomErrorResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        ResponseEntity<CustomErrorResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }
}
