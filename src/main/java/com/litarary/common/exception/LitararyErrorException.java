package com.litarary.common.exception;

import com.litarary.common.ErrorCode;
import lombok.Getter;

@Getter
public class LitararyErrorException extends RuntimeException {
    private final ErrorCode errorCode;
    private String detailMessage;

    public LitararyErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public LitararyErrorException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.detailMessage = message;
    }
}
