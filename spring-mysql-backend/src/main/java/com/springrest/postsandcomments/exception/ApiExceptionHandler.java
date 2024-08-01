package com.springrest.postsandcomments.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApiExceptionHandler {

	// Not Found Exception == HttpStatus.NOT_FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e) {
        ErrorResponse error = new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	// Validation Exception == HttpStatus.BAD_REQUEST
    // .
    // (Triggered when violations to an Entity domain are done through @Valid @RequestBody of a REST controller
	// and/or violations to an Entity domain are done done through "org.hibernate.exception.ConstraintViolationException" package)
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.toString(), "Validation error(s).");
		for (ConstraintViolation fieldError : e.getConstraintViolations()) {
			errorResponse.addErrorItem(fieldError.getMessageTemplate(), fieldError.getMessage());
		}
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Internal Server Error ==  HttpStatus.INTERNAL_SERVER_ERROR for BAD_REQUEST errors 
    // .
    // (Triggered when violations to an Entity domain are done through "jakarta.validation.constraints" package)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {    	
		ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.toString(), "Validation error(s).");
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			errorResponse.addErrorItem(fieldError.getField(), fieldError.getDefaultMessage());
		}
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Internal Server Error ==  HttpStatus.INTERNAL_SERVER_ERROR
    // .
    // Triggered for all the other kinds of unmapped errors that don't fall in the previous defined exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handle(Exception e) {
        ErrorResponse error = new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
