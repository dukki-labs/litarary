package com.litarary.account.controller;

import com.litarary.account.controller.dto.*;
import com.litarary.account.domain.AccessRole;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.service.AccountService;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.account.service.mail.MailSenderService;
import com.litarary.common.ErrorCode;
import com.litarary.common.RestDocsControllerTest;
import com.litarary.common.exception.LitararyErrorException;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends RestDocsControllerTest {

    @MockBean
    private AccountService accountService;
    @MockBean
    private MailSenderService mailSenderService;

    private final String REQUEST_PREFIX = "/api/v1/account";

    @Test
    void signUpTest() throws Exception {

        MemberDto.Request request = MemberDto.Request
                .builder()
                .nickName("test")
                .email("test@naver.com")
                .password("qwer123!@")
                .accessRoles(List.of(AccessRole.TESTER))
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
                                        fieldWithPath("serviceTerms").type(JsonFieldType.BOOLEAN).description("서비스 약관 동의"),
                                        fieldWithPath("privacyTerms").type(JsonFieldType.BOOLEAN).description("개인정보 약관 동의"),
                                        fieldWithPath("serviceAlarm").type(JsonFieldType.BOOLEAN).description("서비스 알림 동의"),
//                                        fieldWithPath("interestItems[]").type(JsonFieldType.ARRAY).description("취미 종류 " +
//                                                "`[HISTORY_CULTURE:가정/요리/뷰티]`\n" +
//                                                "`[HOBBY:취미]`\n" +
//                                                "`[ECONOMY_OPERATE:경제경영]`\n" +
//                                                "`[HIGH_SCHOOL:고등학교참고서]`\n" +
//                                                "`[CLASSIC:고전]`\n" +
//                                                "`[SCIENCE_TECHNOLOGY:과학]`\n" +
//                                                "`[KUMGANG_TRAVEL:금강산여행]`\n" +
//                                                "`[CALENDAR:달력]`\n" +
//                                                "`[UNIVERSITY_BOOK:대학교제]`\n" +
//                                                "`[COMIC_BOOK:만화]`\n" +
//                                                "`[SOCIAL_SCIENCE:사회과학]`\n" +
//                                                "`[NOVEL_POETRY_DRAMA:소설/시/희곡]`\n" +
//                                                "`[CALIFIER_CERTIFICATE:수험서/자격증]`\n" +
//                                                "`[CHILDREN:어린이]`\n" +
//                                                "`[ESSAY:에세이]`\n" +
//                                                "`[TRAVEL:여행]`\n" +
//                                                "`[HISTORY:역사]`\n" +
//                                                "`[ART_POPULAR_CULTURE:예술/대중문화]`\n" +
//                                                "`[FOREIGN_LANGUAGE:외국어]`\n" +
//                                                "`[CHILD:유아]`\n" +
//                                                "`[HUMANITIES:인문학]`\n" +
//                                                "`[JAPANESE_BOOK:일본 도서]`\n" +
//                                                "`[SELF_DEVELOPMENT:자기계발]`\n" +
//                                                "`[MAGAZINE:잡지]`\n" +
//                                                "`[GENRE:장르소설]`\n" +
//                                                "`[COMPLETE_USED_COLLECTION:전집/중고전집]`\n" +
//                                                "`[RELIGION_MECHANICS:종교/역학]`\n" +
//                                                "`[GOOD_PARENT:좋은부모]`\n" +
//                                                "`[MIDDLE_SCHOOL:중학교참고서]`\n" +
//                                                "`[TEENAGER:청소년]`\n" +
//                                                "`[YOUTH_RECOMMENDATION:청소년_추천도서]`\n" +
//                                                "`[Elementary_School:초등학교참고서]`\n" +
//                                                "`[Computer_Mobile:컴퓨터/모바일]`\n" +
//                                                "`[Gift:Gift]`\n" +
//                                                "`[Other:기타]`\n"),
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

    @Test
    void emailCertificationTest() throws Exception {
        String email = "test@test.com";
        Member member = Member.builder()
                .id(1L)
                .nickName("test")
                .email(email)
                .build();
        MemberEmailDto.Request requestDto = MemberEmailDto.Request.builder()
                .email(email)
                .build();
        given(accountService.findMember(anyString())).willReturn(member);
        given(mailSenderService.sendAuthCode(anyString())).willReturn(email);
        doNothing().when(accountService).updateAuthCode(anyLong(), anyString());

        String uri = REQUEST_PREFIX + "/password/send-code";
        String content = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(patch(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("회원 닉네임")
                                )
                        )
                );
    }

    @Test
    void checkAuthCodeTest() throws Exception {
        long memberId = 1L;
        String authCode = "0AD2EF";
        MemberAccessCode.Request request = MemberAccessCode.Request.builder()
                .memberId(memberId)
                .authCode(authCode)
                .build();
        String content = objectMapper.writeValueAsString(request);
        doNothing().when(accountService).checkAuthCode(memberId, authCode);

        String uri = REQUEST_PREFIX + "/check-auth-code";
        mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("authCode").type(JsonFieldType.STRING).description("인증문자")
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
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("변경할 비밀번호"),
                                        fieldWithPath("accessCode").type(JsonFieldType.STRING).description("인증번호")
                                )
                        )
                );
    }


    @Test
    void sendAuthCodeTest() throws Exception {
        String email = "test@gmail.com";
        MemberEmailDto.Request request = MemberEmailDto.Request.builder()
                .email(email)
                .build();
        String content = objectMapper.writeValueAsString(request);
        given(mailSenderService.sendAuthCode(anyString())).willReturn(email);

        String uri = REQUEST_PREFIX + "/send-code";
        mockMvc.perform(RestDocumentationRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("인증문자 전송할 메일주소")
                                )
                        )
                );
    }

    @Test
    void 비밀번호_변경요청시_회원번호는_0이하입력시_에러발생_테스트() throws Exception {
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
    void 비밀번호변경을위해_인증코드전송시_등록되지않는_이메일인경우_에러발생_테스트() throws Exception {
        String email = "test@test.com";
        MemberEmailDto.Request request = MemberEmailDto.Request.builder()
                .email(email)
                .build();
        given(accountService.findMember(email)).willThrow(new LitararyErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));

        String content = objectMapper.writeValueAsString(request);
        String uri = REQUEST_PREFIX + "/password/send-code";
        mockMvc.perform(patch(uri)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("ACCOUNT_NOT_FOUND_EMAIL"))
                .andDo(print());
    }
    @Test
    void 비밀번호_변경요청시_포멧_불일치할경우_에러발생_테스트() throws Exception {
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
    void 비밀번호_변경요청시_엑세스코드_없이_요청하면_에러발생_테스트() throws Exception {
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
    void 인증번호등록_요청시_회원번호가_없이_요청한경우_에러가발생한다() throws Exception {
        MemberAccessCode.Request request = MemberAccessCode.Request.builder()
                .authCode("A3WRV4")
                .build();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(get("/api/v1/account/check-auth-code")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("UN_VALID_BINDING"))
                .andDo(print());
    }
}