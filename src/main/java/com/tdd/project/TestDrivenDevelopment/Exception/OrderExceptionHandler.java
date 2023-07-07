package com.tdd.project.TestDrivenDevelopment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class OrderExceptionHandler {

    //Exception handler for OrderNotFoundException.
    //Returns a 404 Not Found response with an ErrorResponse containing the error message.
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex) {
        String errorMessage = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //Exception handler for BindException.
    //Returns a 400 Bad Request response with an ErrorResponse containing the validation errors.
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        return fieldError.getField() + ": " + error.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("Validation Error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    //Generic exception handler.
    //Returns a 500 Internal Server Error response with an ErrorResponse containing the error message.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
