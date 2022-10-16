package com.example.cinnoxboss.errorHandle.controllerAdvice;

import com.example.cinnoxboss.errorHandle.exception.ErrorCodeException;
import com.example.cinnoxboss.errorHandle.form.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCodeException.class)
    protected ResponseEntity<Object> runtimeExceptionHandle(ErrorCodeException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(ex.getError().getHttpStatusCode());
        errorResponse.setStatusCode(ex.getError().getHttpStatusCode().value());
        errorResponse.setErrorCode(ex.getError().getErrorCode());
        errorResponse.setMessage(ex.getError().getMessage());

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> errorLog(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
        errorResponse.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        errorResponse.setErrorCode(-9999);
        errorResponse.setMessage(ex.getCause().toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
