package com.litarary.account.controller;

import com.litarary.account.controller.dto.*;
import com.litarary.account.domain.AccessRole;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("accountTerms").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                        fieldWithPath("serviceTerms").type(JsonFieldType.BOOLEAN).description("????????? ?????? ??????"),
                                        fieldWithPath("privacyTerms").type(JsonFieldType.BOOLEAN).description("???????????? ?????? ??????"),
                                        fieldWithPath("serviceAlarm").type(JsonFieldType.BOOLEAN).description("????????? ?????? ??????"),
//                                        fieldWithPath("interestItems[]").type(JsonFieldType.ARRAY).description("?????? ?????? " +
//                                                "`[HISTORY_CULTURE:??????/??????/??????]`\n" +
//                                                "`[HOBBY:??????]`\n" +
//                                                "`[ECONOMY_OPERATE:????????????]`\n" +
//                                                "`[HIGH_SCHOOL:?????????????????????]`\n" +
//                                                "`[CLASSIC:??????]`\n" +
//                                                "`[SCIENCE_TECHNOLOGY:??????]`\n" +
//                                                "`[KUMGANG_TRAVEL:???????????????]`\n" +
//                                                "`[CALENDAR:??????]`\n" +
//                                                "`[UNIVERSITY_BOOK:????????????]`\n" +
//                                                "`[COMIC_BOOK:??????]`\n" +
//                                                "`[SOCIAL_SCIENCE:????????????]`\n" +
//                                                "`[NOVEL_POETRY_DRAMA:??????/???/??????]`\n" +
//                                                "`[CALIFIER_CERTIFICATE:?????????/?????????]`\n" +
//                                                "`[CHILDREN:?????????]`\n" +
//                                                "`[ESSAY:?????????]`\n" +
//                                                "`[TRAVEL:??????]`\n" +
//                                                "`[HISTORY:??????]`\n" +
//                                                "`[ART_POPULAR_CULTURE:??????/????????????]`\n" +
//                                                "`[FOREIGN_LANGUAGE:?????????]`\n" +
//                                                "`[CHILD:??????]`\n" +
//                                                "`[HUMANITIES:?????????]`\n" +
//                                                "`[JAPANESE_BOOK:?????? ??????]`\n" +
//                                                "`[SELF_DEVELOPMENT:????????????]`\n" +
//                                                "`[MAGAZINE:??????]`\n" +
//                                                "`[GENRE:????????????]`\n" +
//                                                "`[COMPLETE_USED_COLLECTION:??????/????????????]`\n" +
//                                                "`[RELIGION_MECHANICS:??????/??????]`\n" +
//                                                "`[GOOD_PARENT:????????????]`\n" +
//                                                "`[MIDDLE_SCHOOL:??????????????????]`\n" +
//                                                "`[TEENAGER:?????????]`\n" +
//                                                "`[YOUTH_RECOMMENDATION:?????????_????????????]`\n" +
//                                                "`[Elementary_School:?????????????????????]`\n" +
//                                                "`[Computer_Mobile:?????????/?????????]`\n" +
//                                                "`[Gift:Gift]`\n" +
//                                                "`[Other:??????]`\n"),
                                        fieldWithPath("accessRoles.[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? [USER, TESTER]")
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
                                fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                        ),
                        responseFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("memberEmail").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("grantType").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("expiredAccessTokenAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ??????"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("?????? ??????")
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
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("?????? ??????")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("????????? ??????")
                                )
                        )
                );
    }

    @Test
    void findMemberByEmailTest() throws Exception {
        String email = "test@test.com";
        Member member = Member.builder()
                .id(1L)
                .nickName("test")
                .email(email)
                .build();
        given(accountService.findMember(anyString())).willReturn(member);

        mockMvc.perform(get(REQUEST_PREFIX)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("email").description("?????????")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("?????? ?????????")
                                )
                        )
                );
    }

    @Test
    void updateAccessCodeTest() throws Exception {
        long memberId = 1L;
        String accessCode = "1515134";
        MemberAccessCode.Request request = MemberAccessCode.Request.builder()
                .memberId(memberId)
                .accessCode(accessCode)
                .build();
        String content = objectMapper.writeValueAsString(request);
        doNothing().when(accountService).updateAccessCode(memberId, accessCode);

        String uri = REQUEST_PREFIX + "/access-code";
        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("accessCode").type(JsonFieldType.STRING).description("????????????")
                                )
                        )
                );
    }

    @Test
    void updatePasswordTest() throws Exception {
        MemberPasswordDto.Request request = MemberPasswordDto.Request.builder()
                .memberId(1L)
                .password("test123!")
                .accessCode("3425235")
                .build();
        String content = objectMapper.writeValueAsString(request);
        doNothing().when(accountService).updatePassword(anyLong(), anyString(), anyString());

        String uri = REQUEST_PREFIX + "/password";
        mockMvc.perform(RestDocumentationRequestBuilders.patch(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????? ????????????"),
                                        fieldWithPath("accessCode").type(JsonFieldType.STRING).description("????????????")
                                )
                        )
                );
    }

    @Test
    void ????????????_???????????????_???????????????_0???????????????_????????????_?????????() throws Exception {
        MemberPasswordDto.Request request = MemberPasswordDto.Request.builder()
                .memberId(0L)
                .password("test123")
                .accessCode("3425235")
                .build();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(patch("/api/v1/account/password")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("UN_VALID_BINDING"))
                .andDo(print());

    }

    @Test
    void ????????????_???????????????_??????_??????????????????_????????????_?????????() throws Exception {
        MemberPasswordDto.Request request = MemberPasswordDto.Request.builder()
                .memberId(1L)
                .password("test123")
                .accessCode("3425235")
                .build();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(patch("/api/v1/account/password")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("UN_VALID_BINDING"))
                .andDo(print());
    }

    @Test
    void ????????????_???????????????_???????????????_??????_????????????_????????????_?????????() throws Exception {
        MemberPasswordDto.Request request = MemberPasswordDto.Request.builder()
                .memberId(1L)
                .password("test123")
                .build();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(patch("/api/v1/account/password")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("UN_VALID_BINDING"))
                .andDo(print());
    }

    @Test
    void ??????????????????_?????????_???????????????_??????_???????????????_?????????????????????() throws Exception {
        MemberAccessCode.Request request = MemberAccessCode.Request.builder()
                .accessCode("21415")
                .build();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(patch("/api/v1/account/access-code")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("UN_VALID_BINDING"))
                .andDo(print());
    }
}