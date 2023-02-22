package com.litarary.account.controller.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request {
        @NotBlank
        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일이 잘못 입력되었어요!")
        private String email;

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$", message = "패스워드가 잘못 입력되었어요!")
        private String password;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response {
        private long memberId;
        private String memberEmail;
        private String memberNickName;
        private String accessToken;
        private String refreshToken;
        private String grantType;
        private LocalDateTime expiredAccessTokenAt;
    }
}
