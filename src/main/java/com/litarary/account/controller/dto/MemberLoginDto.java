package com.litarary.account.controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request {
        @Email
        private String email;

        @NotBlank
        private String password;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response {
        private String accessToken;
        private String refreshToken;
        private String grantType;
        private LocalDateTime expiredAccessTokenAt;
    }
}
