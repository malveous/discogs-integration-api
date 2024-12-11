package com.mtataje.discogs.integration.api.exception.handler;

import com.mtataje.discogs.integration.api.exception.*;
import com.mtataje.discogs.integration.api.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleSQLIntegrityConstraintViolationException(DataIntegrityViolationException e) {
        log.debug("An error occurred while persisting data in data source", e);
        return new ErrorResponse("Data entered for the user is not valid for registration");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {InvalidHttpStatusCodeException.class, NoResponseDataException.class,
            ResponseWithoutBodyException.class, ConcurrentOperationFailureException.class})
    public ErrorResponse handle(RuntimeException e) {
        log.debug("An unexpected error occurred", e);
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        log.debug("An error occurred while validating input fields", e);
        var errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(";"));
        return new ErrorResponse(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedSourceForIntegrationAPIException.class)
    public ErrorResponse handleValidationExceptions(UnsupportedSourceForIntegrationAPIException e) {
        log.debug("The source requested is not valid or supported", e);
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtistNotFoundException.class)
    public ErrorResponse handleValidationExceptions(ArtistNotFoundException e) {
        log.debug("An artist was not found for the current request", e);
        return new ErrorResponse(e.getMessage());
    }

}
