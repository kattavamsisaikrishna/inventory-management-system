package com.vamsi.inventory.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(),error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String,String>> handleTypeMisMatchException(MethodArgumentTypeMismatchException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error","Please Provide the correct value");
        return ResponseEntity.badRequest().body(errors);
    }
    

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFoundException(ProductNotFoundException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleProductAlreadyExistsException(ProductAlreadyExistsException ex){
        Map<String ,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}
