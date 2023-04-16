package com.litarary.category.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.Category;
import com.litarary.category.service.CategoryService;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.common.RestDocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
}