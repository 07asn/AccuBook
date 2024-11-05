package com.xocialive.accubook.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "An error occurred due to data integrity violation.";

        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        if (rootMessage != null) {
            if (rootMessage.contains("user_unique_email") || rootMessage.contains("unique constraint") || rootMessage.contains("duplicate")) {
                message = "User with this email already exists.";
            }
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<String> handleHibernateException(HibernateException ex) {
        String message = "Database error: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            message.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append(". ");
        }
        return new ResponseEntity<>(message.toString().trim(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        String message = "An error occurred.";

        String rootMessage = ex.getReason() != null ? ex.getReason() : ex.getMessage();

        if (rootMessage != null) {
            if (rootMessage.contains("Client with ID")) {
                message = "The specified client was not found.";
            } else if (rootMessage.contains("You do not have permission")) {
                message = "Access denied: You do not have permission to access this resource.";
            }
        }
        return new ResponseEntity<>(message, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}

