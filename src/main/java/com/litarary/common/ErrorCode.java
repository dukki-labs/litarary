package com.litarary.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //Account Error
    ACCOUNT_NOT_FOUND_EMAIL("현재 등록되지 않은 이메일이에요!"),
    DUPLICATED_EMAIL("이미 가입된 이메일 입니다."),
    ACCOUNT_ACCESS_ROLE_MISS_MATCH("해당 사용자는 권한을 가지고 있지 않습니다."),
    MEMBER_NOT_FOUND("등록된 회원이 없습니다."),
    MISS_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),

    //JWT Error
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALIDATED_TOKEN("일치하지 않는 토큰입니다."),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰입니다."),
    REFRESH_TOKEN_EXPIRED("Refresh 토큰이 만료되었습니다. 다시 로그인해주세요."),

    // Controller binding Error
    UN_VALID_BINDING("요청 파라미터 혹은 Body 부분이 잘못되었습니다."),
    REQUEST_INVALID_JSON("Json 요청 바디 부분이 잘못되었습니다."),

    // Book Error
    JSON_PARSING_ERROR("Json Parse에 실패했습니다."),
    EXTERNAL_REQUEST_ERROR("외부 API요청에 실패했습니다.");
    private final String message;
}
