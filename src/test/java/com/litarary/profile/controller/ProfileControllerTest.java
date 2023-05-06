package com.litarary.profile.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.common.RestDocsControllerTest;
import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.CategoryInfo;
import com.litarary.profile.service.dto.MemberProfileDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
}