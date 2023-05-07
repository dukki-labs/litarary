package com.litarary.category.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.Category;
import com.litarary.category.service.CategoryService;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.common.RestDocsControllerTest;
import com.litarary.common.dummy.DummyPageBookInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends RestDocsControllerTest {

    @MockBean
    private CategoryService categoryService;
    private final String BASE_URI = "/api/v1/categories";


    @Test
    @WithMockUser
    void interestCategoryTest() throws Exception {
        final String uri = BASE_URI + "/interest";
        final Long memberId = 1L;
        List<Category> categories = List.of(Category.builder()
                .id(1L)
                .bookCategory(BookCategory.COMPUTER_MOBILE)
                .build());

        when(categoryService.findInterestCategory(anyLong()))
                .thenReturn(
                        MemberCategoryInfo.builder()
                                .memberId(memberId)
                                .nickName("nickName")
                                .categoryList(categories)
                                .build()
                );

        mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .requestAttr("memberId", 1L))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("memberId").description("회원 ID"),
                                        fieldWithPath("memberName").description("회원 닉네임"),
                                        fieldWithPath("categoryList[].id").description("회원의 관심 카테고리 고유번호"),
                                        fieldWithPath("categoryList[].bookCategory").description("회원의 관심 카테고리 이름")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    void bookListInCategoryTest() throws Exception {

        final String uri = BASE_URI + "/{categoryId}/books";
        final long memberId = 1L;
        final long categoryId = 1L;

        when(categoryService.findCategoryInBooks(any())).thenReturn(
                DummyPageBookInfo.of()
        );

        mockMvc.perform(get(uri, categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .requestAttr("memberId", memberId)
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("categoryId").description("카테고리 고유번호")
                                ),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지당 데이터 개수")
                                ),
                                responseFields(
                                        fieldWithPath("page").type(JsonFieldType.NUMBER).description("요청한 페이지 번호"),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("요청한 페이지당 데이터 개수"),
                                        fieldWithPath("totalPage").type(JsonFieldType.NUMBER).description("전체 페이지 개수"),
                                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 도서 개수"),
                                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("contents[].id").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                        fieldWithPath("contents[].title").type(JsonFieldType.STRING).description("책 제목"),
                                        fieldWithPath("contents[].imageUrl").type(JsonFieldType.STRING).description("책 이미지 URL"),
                                        fieldWithPath("contents[].content").type(JsonFieldType.STRING).description("책 설명"),
                                        fieldWithPath("contents[].review").type(JsonFieldType.STRING).description("리뷰"),
                                        fieldWithPath("contents[].deadLine").type(JsonFieldType.STRING).description("대출 기한 [ONE_WEEK: 1주] \n [TWO_WEEK: 2주] \n [THREE_WEEK: 3주] \n [FOUR_WEEK: 4주]"),
                                        fieldWithPath("contents[].author").type(JsonFieldType.STRING).description("저자"),
                                        fieldWithPath("contents[].rentalUseYn").type(JsonFieldType.STRING).description("대여 가능 여부 \n" +
                                                "`[Y: 대여 가능]` \n" +
                                                "`[N: 대여 불가능]`"),
                                        fieldWithPath("contents[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                        fieldWithPath("contents[].publishDate").type(JsonFieldType.STRING).description("출판일"),
                                        fieldWithPath("contents[].returnLocation").type(JsonFieldType.STRING).description("반납 장소"),
                                        fieldWithPath("contents[].recommendCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("contents[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 고유번호"),
                                        fieldWithPath("contents[].category").type(JsonFieldType.STRING).description("카테고리"),
                                        fieldWithPath("contents[].createdAt").type(JsonFieldType.STRING).description("도서 등록날짜"),
                                        fieldWithPath("contents[].newTag").type(JsonFieldType.STRING).description("[신규: NEW] \n [일반도서: NORMAL]")
                                )
                        )
                );
    }
}