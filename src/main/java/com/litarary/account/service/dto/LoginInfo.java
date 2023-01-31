package com.litarary.account.service.dto;

import com.litarary.account.domain.entity.Member;
import com.litarary.utils.jwt.TokenInfo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginInfo {
    private TokenInfo tokenInfo;
    private Member member;
}
