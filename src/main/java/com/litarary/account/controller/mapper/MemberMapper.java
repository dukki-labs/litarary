package com.litarary.account.controller.mapper;

import com.litarary.account.controller.dto.MemberDto;
import com.litarary.account.controller.dto.MemberEmailDto;
import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.account.controller.dto.MemberTokenDto;
import com.litarary.account.domain.UseYn;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.utils.jwt.TokenInfo;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class MemberMapper {
    public SignUpMemberInfo mapToSignUpMember(MemberDto.Request request) {
        return SignUpMemberInfo.builder()
                .memberId(request.getMemberId())
                .member(Member.builder()
                        .nickName(request.getNickName())
                        .email(request.getEmail())
                        .useYn(UseYn.Y)
                        .password(request.getPassword())
                        .isServiceTerms(request.isServiceTerms())
                        .isPrivacyTerms(request.isPrivacyTerms())
                        .isServiceAlarm(request.isServiceAlarm())
                        .createdAt(OffsetDateTime.now())
                        .updatedAt(OffsetDateTime.now())
                        .build())
                .bookCategoryList(request.getBookCategoryList())
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

    public MemberTokenDto.Response refreshTokenResponse(RefreshTokenInfo refreshTokenInfo) {
        return MemberTokenDto.Response
                .builder()
                .memberId(refreshTokenInfo.getMemberId())
                .email(refreshTokenInfo.getEmail())
                .accessToken(refreshTokenInfo.getAccessToken())
                .build();
    }

    public MemberEmailDto.Response memberResponse(Member member) {
        return MemberEmailDto.Response
                .builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .memberNickName(member.getNickName())
                .build();
    }
}
