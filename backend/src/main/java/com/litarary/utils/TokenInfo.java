package com.litarary.utils;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TokenInfo {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private LocalDateTime expiredAccessTokenAt;
}
