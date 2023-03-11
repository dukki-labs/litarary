package com.litarary.book.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.service.BookService;
import com.litarary.book.service.dto.ContainerBook;
import com.litarary.book.service.dto.ContainerBookInfo;
import com.litarary.common.RestDocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends RestDocsControllerTest {

    @Test
    @WithMockUser
    void recentBookListTest() throws Exception {
        mockMvc.perform(get("/api/v1/books/recent")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("inquiryDate", "2023-02-23"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("inquiryDate").description("조회 요청 날짜")
                        ),
                        responseFields(
                                fieldWithPath("bookInfoDtoList.[].imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                fieldWithPath("bookInfoDtoList.[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("bookInfoDtoList.[].bookCategory").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("bookInfoDtoList.[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("bookInfoDtoList.[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                                fieldWithPath("bookInfoDtoList.[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("bookInfoDtoList.[].regDt").type(JsonFieldType.STRING).description("등록 날짜")

                        )
                ));
    }

    @Test
    @WithMockUser
    void concernBookListTest() throws Exception {

        mockMvc.perform(get("/api/v1/books/concern")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("inquiryDate", "2023-02-23"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("inquiryDate").description("조회 요청 날짜")
                        ),
                        responseFields(
                                fieldWithPath("concernBookTypeDto.[].bookCategory").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].imageUrl")
                                        .type(JsonFieldType.STRING).description("도서 썸네일 URL"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].title")
                                        .type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].bookCategory")
                                        .type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].content")
                                        .type(JsonFieldType.STRING).description("본문 내용"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].likeCount")
                                        .type(JsonFieldType.NUMBER).description("좋아요 갯수"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].viewCount")
                                        .type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].regDt")
                                        .type(JsonFieldType.STRING).description("등록 날짜")
                        )
                ));
    }
}