package com.litarary.account.controller.mapper;

import com.litarary.account.controller.dto.MemberDto;
import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.utils.TokenInfo;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public SignUpMemberInfo mapToSignUpMember(MemberDto.Request request) {
        return SignUpMemberInfo.builder()
                .member(Member.builder()
                        .nickName(request.getNickName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .isAccountTerms(request.isAccountTerms())
                        .isServiceTerms(request.isServiceTerms())
                        .isPrivacyTerms(request.isPrivacyTerms())
                        .isServiceAlarm(request.isServiceAlarm())
                        .build())
                .interests(request.getInterestItems())
                .accessRoles(request.getAccessRoles())
                .build();
    }

    public MemberLoginDto.Response loginResponse(TokenInfo login) {
        return MemberLoginDto.Response
                .builder()
                .accessToken(login.getAccessToken())
                .refreshToken(login.getRefreshToken())
                .grantType(login.getGrantType())
                .expiredAccessTokenAt(login.getExpiredAccessTokenAt())
                .build();
    }
}
