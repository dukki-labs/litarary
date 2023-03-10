package com.litarary.account.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberTokenDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Request {
        @NotBlank
        private String email;
        @NotBlank
        private String refreshToken;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private long memberId;
        private String email;
        private String accessToken;
    }
}
