package com.litarary.common.exception;

import com.litarary.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = LitararyErrorException.class)
    public ErrorResponse litararyExceptionHandle(LitararyErrorException ex) {
        log.warn(">>>>> LitararyErrorException service error = {}", ex.getErrorCode().getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ex.getErrorCode().name())
                .errorMessage(ex.getErrorCode().getMessage())
                .detailErrorMessage(ex.getDetailMessage())
                .build();
    }

    // Valid 애노테이션으로 인해 파라미터 바인딩 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse validExceptionHandle(MethodArgumentNotValidException ex) {
        log.warn(">>>>> MethodArgumentNotValidException error = {}", ex.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ErrorCode.UN_VALID_BINDING.name())
                .errorMessage(ErrorCode.UN_VALID_BINDING.getMessage())
                .errorFields(
                        ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new ErrorField(error.getField(), error.getDefaultMessage()))
                                .toList())
                .build();
    }


    //Json Body 요청 검증 위반에 대한 예외 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ErrorResponse messageReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.warn(">>>>> HttpMessageNotReadableException error = {}", ex.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ErrorCode.REQUEST_INVALID_JSON.name())
                .errorMessage(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse exceptionHandler(Exception ex) {
        log.warn(">>>>> Exception error = {}", ex.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errorMessage(ex.getMessage())
                .build();
    }
}
