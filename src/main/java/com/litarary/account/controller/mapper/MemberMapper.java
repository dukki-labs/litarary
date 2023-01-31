package com.litarary.account.controller.mapper;

import com.litarary.account.controller.dto.MemberDto;
import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.utils.jwt.TokenInfo;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

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
                        .createdAt(OffsetDateTime.now())
                        .updatedAt(OffsetDateTime.now())
                        .build())
                .interests(request.getInterestItems())
                .accessRoles(request.getAccessRoles())
                .build();
    }

    public MemberLoginDto.Response loginResponse(LoginInfo loginInfo) {
        Member member = loginInfo.getMember();
        TokenInfo tokenInfo = loginInfo.getTokenInfo();
        return MemberLoginDto.Response
                .builder()
                .memberId(member.getId())
                .memberEmail(member.getEmail())
                .memberNickName(member.getNickName())
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .grantType(tokenInfo.getGrantType())
                .expiredAccessTokenAt(tokenInfo.getExpiredAccessTokenAt())
                .build();
    }
}
