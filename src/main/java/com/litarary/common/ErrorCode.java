package com.litarary.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALIDATED_TOKEN("일치하지 않는 토큰입니다."),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰입니다."),
    AUTH_INVALIDATED_TOKEN("인증에 실패했습니다.")
    ;
    private final String message;
}
