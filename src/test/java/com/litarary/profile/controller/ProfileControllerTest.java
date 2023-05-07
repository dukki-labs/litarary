package com.litarary.profile.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.common.RestDocsControllerTest;
import com.litarary.common.dummy.DummyPageBookInfo;
import com.litarary.profile.controller.dto.UpdateProfileDto;
import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.CategoryInfo;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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


    @Test
    void registerBooksTest() throws Exception {
        final String uri = BASE_URI + "/profile/register/books";

        when(profileService.registerBooks(anyLong(), any(PageRequest.class), any(RegisterDate.class)))
                .thenReturn(DummyPageBookInfo.of());

        mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .requestAttr("memberId", 1L)
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("registerDate", RegisterDate.NONE.name()))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 사이즈"),
                                        parameterWithName("registerDate").description("등록 검색 조건 \n" +
                                                "`[NONE: 전체]`\n\n" +
                                                "`[ONE_MONTH: 1개월]`\n\n" +
                                                "`[THREE_MONTH: 3개월]`\n\n" +
                                                "`[SIX_MONTH: 6개월]`\n\n")
                                ),
                                responseFields(
                                        fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                        fieldWithPath("totalPage").type(JsonFieldType.NUMBER).description("페이지 갯수"),
                                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("페이지 항목 갯수"),
                                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 유무"),
                                        fieldWithPath("contents[].id").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                        fieldWithPath("contents[].title").type(JsonFieldType.STRING).description("도서 제목"),
                                        fieldWithPath("contents[].imageUrl").type(JsonFieldType.STRING).description("도서 썸네일 URL"),
                                        fieldWithPath("contents[].content").type(JsonFieldType.STRING).description("도서 설명"),
                                        fieldWithPath("contents[].review").type(JsonFieldType.STRING).description("도서 소개"),
                                        fieldWithPath("contents[].deadLine").type(JsonFieldType.STRING).description("반납 기한 \n" +
                                                " `[ONE_WEEK: 1주일]` \n" +
                                                " `[TWO_WEEK: 2주일]` \n" +
                                                " `[THREE_WEEK: 3주일]`\n" +
                                                " `[FOUR_WEEK: 4주일]` "),
                                        fieldWithPath("contents[].author").type(JsonFieldType.STRING).description("저자"),
                                        fieldWithPath("contents[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                        fieldWithPath("contents[].publishDate").type(JsonFieldType.STRING).description("출판일"),
                                        fieldWithPath("contents[].returnLocation").type(JsonFieldType.STRING).description("반납 장소"),
                                        fieldWithPath("contents[].recommendCount").type(JsonFieldType.NUMBER).description("추천 갯수"),
                                        fieldWithPath("contents[].rentalUseYn").type(JsonFieldType.STRING).description("대여 가능 여부 \n" +
                                                "`[Y: 대여 가능]` \n" +
                                                "`[N: 대여 불가능]`"),
                                        fieldWithPath("contents[].createdAt").type(JsonFieldType.STRING).description("도서 등록일자"),
                                        fieldWithPath("contents[].categoryId").type(JsonFieldType.NUMBER).description("도서 카테고리 고유번호"),
                                        fieldWithPath("contents[].category").type(JsonFieldType.STRING).description("도서 카테고리"),
                                        fieldWithPath("contents[].newTag").type(JsonFieldType.STRING).description("`[신규: NEW]` \n `[일반도서: NORMAL]`")
                                )
                        )
                );
    }
}