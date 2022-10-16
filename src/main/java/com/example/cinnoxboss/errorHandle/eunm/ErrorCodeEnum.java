package com.example.cinnoxboss.errorHandle.eunm;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum {

    USER_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, -10000, "查無此使用者訊息"),
    LINE_SEND_MESSAGE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, -10001, "Line發送訊息異常");

    @Getter
    private final HttpStatus httpStatusCode;
    @Getter
    private final Integer errorCode;
    @Getter
    private final String message;

    ErrorCodeEnum(HttpStatus httpStatusCode, Integer errorCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
