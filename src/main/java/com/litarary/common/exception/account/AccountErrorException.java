package com.litarary.common.exception.account;

import com.litarary.common.ErrorCode;
import lombok.Getter;

@Getter
public class AccountErrorException extends RuntimeException {
    private final ErrorCode errorCode;

    public AccountErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
