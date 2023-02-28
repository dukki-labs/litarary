package com.litarary.account.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberAccessCode {

    @Builder
    @Getter
    public static class Request {

        @Min(1)
        public long memberId;
        @NotBlank(message = "인증번호는 필수값 입니다.")
        public String accessCode;
    }
}
