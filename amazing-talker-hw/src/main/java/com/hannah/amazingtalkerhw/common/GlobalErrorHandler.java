package com.hannah.amazingtalkerhw.common;

import com.hannah.amazingtalkerhw.exception.BadRequestException;
import com.hannah.amazingtalkerhw.exception.OAuth2AuthenticationProcessingException;
import com.hannah.amazingtalkerhw.payload.ApiErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintError(ConstraintViolationException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestError(BadRequestException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<Object> handleAuthenticationError(OAuth2AuthenticationProcessingException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage("error message");

        return new ResponseEntity<>(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }
}