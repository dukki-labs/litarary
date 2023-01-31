package com.litarary.common.exception.filter;

import com.litarary.common.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class JwtErrorException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public JwtErrorException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        log.info("ErrorCode = {}, message = {}", errorCode.getMessage(), message);
    }

    public JwtErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        log.info("ErrorCode = {}, message = {}", errorCode.getMessage(), message);
    }
}
