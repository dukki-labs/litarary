package com.litarary.account.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefreshTokenInfo {
    private long memberId;
    private String email;
    private String accessToken;
}
