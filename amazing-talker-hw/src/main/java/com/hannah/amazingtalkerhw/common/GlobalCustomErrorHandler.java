package com.hannah.amazingtalkerhw.common;

import com.hannah.amazingtalkerhw.exception.BadRequestException;
import com.hannah.amazingtalkerhw.payload.ApiErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalCustomErrorHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> someMethod(ConstraintViolationException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> someMethod(BadRequestException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }
}