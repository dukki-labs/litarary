package com.litarary.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ErrorResponse {
    private int status;
    private String errorCode;
    private String errorMessage;
    private String detailErrorMessage;
    private List<ErrorField> errorFields;
}
