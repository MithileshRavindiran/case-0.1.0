package com.afkl.travel.exercise.exception.handler;

import com.afkl.travel.exercise.exception.ErrorResponse;
import com.afkl.travel.exercise.exception.LocationApplicationBadRequestException;
import com.afkl.travel.exercise.exception.LocationApplicationNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * Handle validation failure exceptions which returns 400 server error
     *
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse response =  ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .build();
        response.addValidationErrors(ex.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle validation failure exceptions which returns 500 server error
     *
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ErrorResponse response =  ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message("Validation errors at the DTO objects")
                .build();
        response.addValidationErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocationApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> customExceptionFromTheApplications(final LocationApplicationNotFoundException ex) {
        log.error("Not Found Exception Occurred: {} ", ex.getMessage());
        ErrorResponse response =  ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({LocationApplicationBadRequestException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> customBadRequestExceptionFromTheApplications(final LocationApplicationBadRequestException ex) {
        log.error("Bad Request Exception Occurred: {} ", ex.getMessage());
        ErrorResponse response =  ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


    /**
     * Handle validation failure exceptions which returns 500 server error
     *
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Internal Server Exception Occurred: {} ", ex.getMessage());
        ErrorResponse response =  ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
