package com.litarary.account.controller;

import com.litarary.account.controller.dto.MemberDto;
import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.account.controller.dto.MemberTokenDto;
import com.litarary.account.domain.AccessRole;
import com.litarary.account.domain.InterestType;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.service.AccountService;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.common.RestDocsControllerTest;
import com.litarary.utils.jwt.TokenInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends RestDocsControllerTest {

    @MockBean
    private AccountService accountService;

    private final String REQUEST_PREFIX = "/api/v1/account";

    @Test
    void signUpTest() throws Exception {

        MemberDto.Request request = MemberDto.Request
                .builder()
                .nickName("test")
                .email("test@naver.com")
                .password("qwer123!@")
                .accessRoles(List.of(AccessRole.TESTER))
                .interestItems(List.of(InterestType.IT, InterestType.CRIME))
                .accountTerms(true)
                .serviceTerms(true)
                .privacyTerms(true)
                .serviceAlarm(true)
                .build();

        doNothing().when(accountService).signUpMember(any(SignUpMemberInfo.class));

        String requestBody = objectMapper.writeValueAsString(request);

        String uri = REQUEST_PREFIX + "/sign-up";
        mockMvc.perform(post(uri)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("accountTerms").type(JsonFieldType.BOOLEAN).description("계정 약관 동의"),
                                        fieldWithPath("serviceTerms").type(JsonFieldType.BOOLEAN).description("서비스 약관 동의"),
                                        fieldWithPath("privacyTerms").type(JsonFieldType.BOOLEAN).description("개인정보 약관 동의"),
                                        fieldWithPath("serviceAlarm").type(JsonFieldType.BOOLEAN).description("서비스 알림 동의"),
                                        fieldWithPath("interestItems[]").type(JsonFieldType.ARRAY).description("취미 종류 [HORROR, GAG, ROMANCE, IT]"),
                                        fieldWithPath("accessRoles.[]").type(JsonFieldType.ARRAY).description("가입 권한 정보 [USER, TESTER]")
                                )
                        )
                );
    }

    @Test
    void loginTest() throws Exception {

        MemberLoginDto.Request request = MemberLoginDto.Request.builder()
                .email("test@namver.com")
                .password("qwer123!@")
                .build();
        String requestBody = objectMapper.writeValueAsString(request);

        LoginInfo loginInfo = LoginInfo.builder()
                .tokenInfo(TokenInfo.builder()
                        .accessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                        .expiredAccessTokenAt(LocalDateTime.now())
                        .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                        .grantType("Bearer")
                        .build())
                .member(
                        Member.builder()
                                .id(1L)
                                .nickName("test")
                                .email("test@naver.com")
                                .build()
                )
                .build();

        given(accountService.login(any(String.class), any(String.class))).willReturn(loginInfo);

        String uri = REQUEST_PREFIX + "/login";
        mockMvc.perform(post(uri)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("grantType").type(JsonFieldType.STRING).description("토큰 타입"),
                                fieldWithPath("expiredAccessTokenAt").type(JsonFieldType.STRING).description("액세스 토큰 만료 날짜"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("갱신 토큰")
                        )
                ));
    }

    @Test
    void refreshTokenTest() throws Exception {
        MemberTokenDto.Request request = MemberTokenDto.Request.builder()
                .email("test@naver.com")
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .build();
        String requestBody = objectMapper.writeValueAsString(request);
        String uri = REQUEST_PREFIX + "/token-refresh";

        RefreshTokenInfo refreshTokenInfo = RefreshTokenInfo.builder()
                .memberId(1L)
                .email("test@naver.com")
                .accessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .build();

        given(accountService.refreshToken(request.getEmail(), request.getRefreshToken())).willReturn(refreshTokenInfo);

        mockMvc.perform(post(uri)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("갱신 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
                                )
                        )
                );
    }
}