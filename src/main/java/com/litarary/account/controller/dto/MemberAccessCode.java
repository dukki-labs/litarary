package com.litarary.account.controller.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberAccessCode {

    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Request {

        @Min(1)
        public long memberId;
        @NotBlank(message = "인증문자는 필수값 입니다.")
        public String authCode;
    }
}
