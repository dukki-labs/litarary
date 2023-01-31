package com.litarary.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //Account Error
    ACCOUNT_NOT_FOUND_EMAIL("등록된 이메일을 찾을 수 없습니다."),
    ACCOUNT_ACCESS_ROLE_MISS_MATCH("해당 사용자는 권한을 가지고 있지 않습니다."),

    //JWT Error
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALIDATED_TOKEN("일치하지 않는 토큰입니다."),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰입니다."),
    AUTH_INVALIDATED_TOKEN("인증에 실패했습니다."),

    // Controller binding Error
    UN_VALID_BINDING("요청 파라미터 혹은 Body 부분이 잘못되었습니다."),
    REQUEST_INVALID_JSON("Json 요청 바디 부분이 잘못되었습니다.");
    private final String message;
}
