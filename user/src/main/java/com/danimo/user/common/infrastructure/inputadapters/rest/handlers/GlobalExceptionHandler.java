package com.danimo.user.common.infrastructure.inputadapters.rest.handlers;

import com.danimo.user.common.application.exceptions.UserAlreadyExistsException;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.common.infrastructure.inputadapters.rest.dto.RestApiError;
import com.danimo.user.common.infrastructure.inputadapters.rest.dto.ValidationDataError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestApiError> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RestApiError(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<RestApiError> handleUserAlreadyExistException(UserAlreadyExistsException e) {
        
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new RestApiError(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationDataError> handleVAlidationConstraintException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(error -> error.getMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationDataError(errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ValidationDataError> handleIllegalArgumentException(IllegalArgumentException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationDataError(errors));
    }
}
