package com.litarary.profile.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.common.RestDocsControllerTest;
import com.litarary.profile.controller.dto.UpdateProfileDto;
import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.CategoryInfo;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest extends RestDocsControllerTest {

    private static final String BASE_URI = "/api/v1";

    @MockBean
    private ProfileService profileService;

    @Test
    void findUserProfileTest() throws Exception {
        final String uri = BASE_URI + "/profile";
        final long memberId = 1L;
        List<CategoryInfo> categoryInfos = List.of(new CategoryInfo(1L, BookCategory.COMPUTER_MOBILE), new CategoryInfo(2L, BookCategory.EDUCATION));

        when(profileService.findUserProfile(memberId))
                .thenReturn(
                        MemberProfileDto.builder()
                                .nickName("test")
                                .email("test@test.com")
                                .categoryInfoList(categoryInfos)
                                .build()
                );

        mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .requestAttr("memberId", 1L))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("categoryInfoList[].categoryId").type(JsonFieldType.NUMBER).description("도서 카테고리 고유번호"),
                                        fieldWithPath("categoryInfoList[].bookCategory").type(JsonFieldType.STRING).description("도서 카테고리")
                                )
                        )
                );
    }

    @Test
    void updateProfileTest() throws Exception {
        final String uri = BASE_URI + "/profile";

        UpdateProfileDto.Request request = UpdateProfileDto.Request.builder()
                .nickName("changeNickName")
                .password("change!1")
                .bookCategoryList(List.of(BookCategory.COMPUTER_MOBILE, BookCategory.EDUCATION))
                .build();

        doNothing().when(profileService).updateProfile(anyLong(), any(UpdateProfile.class));

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .requestAttr("memberId", 1L)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("bookCategoryList[]").type(JsonFieldType.ARRAY).description("관심 카테고리 \n\n" +
                                                        "`[HISTORY_CULTURE:역사/예술/문화]`\n\n" +
                                                        "`[EDUCATION:교육]`\n\n" +
                                                        "`[FAMILY_LIFE:가정/요리/뷰티]`\n\n" +
                                                        "`[HOBBY:건강/취미/레저/여행]`\n\n" +
                                                        "`[ECONOMIC_MANAGEMENT:경제경영]`\n\n" +
                                                        "`[SCIENCE_TECHNOLOGY:사회과학/과학]`\n\n" +
                                                        "`[COMPUTER_MOBILE:컴퓨터/모바일]`\n\n" +
                                                        "`[LITERATURE:문학]`\n\n" +
                                                        "`[ENTERTAINMENT:엔터테인먼트]`\n\n" +
                                                        "`[SELF_DEVELOPMENT:자기계발]`\n\n" +
                                                        "`[LANGUAGE:언어]`\n\n" +
                                                        "`[OTHER:기타]`")
                                )
                        )
                );
    }
}