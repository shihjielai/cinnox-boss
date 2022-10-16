package com.example.cinnoxboss.errorHandle.exception;

import com.example.cinnoxboss.errorHandle.eunm.ErrorCodeEnum;
import lombok.Getter;

public class ErrorCodeException extends RuntimeException {

    @Getter
    private final ErrorCodeEnum error;

    public ErrorCodeException(String message, ErrorCodeEnum error) {
        super(message);
        this.error = error;
    }

}
