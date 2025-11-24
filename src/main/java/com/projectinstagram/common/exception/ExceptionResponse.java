package com.projectinstagram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final int status;
    private final String code;
    private final String message;

    /*
    public ExceptionResponse(ExceptionMessageEnum errorCode, String message) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = message;
    }

     */
}
