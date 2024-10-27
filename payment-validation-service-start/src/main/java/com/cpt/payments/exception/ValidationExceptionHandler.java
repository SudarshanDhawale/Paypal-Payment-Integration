package com.cpt.payments.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.pojo.ErrorResponse;

@ControllerAdvice
public class ValidationExceptionHandler {
	@ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        // Prepare response body with errorCode and errorMessage
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        // Return ResponseEntity with the error response and appropriate HTTP status
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
