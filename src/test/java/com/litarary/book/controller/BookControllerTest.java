package com.litarary.book.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.controller.dto.BookRegistrationDto;
import com.litarary.book.domain.entity.DeadLine;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends RestDocsControllerTest {

    @MockBean
    private BookService bookService;
    private final String BASE_URI = "/api/v1";

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

    @Test
    @WithMockUser
    void bookRegistrationTest() throws Exception {
        String uri = BASE_URI + "/categories/{categoryId}/books";
        BookRegistrationDto.Request request = BookRegistrationDto.Request.builder()
                .title("도서제목")
                .imageUrl("http://test.com")
                .content("본문내용")
                .review("소감 글")
                .deadLine(DeadLine.ONE_WEEK)
                .author("저자명")
                .publisher("출판사")
                .publishDate(LocalDate.now())
                .returnLocation("반납장소")
                .memberId(1L)
                .build();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(uri, 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("categoryId").description("카테고리 고유번호")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("본문내용"),
                                        fieldWithPath("review").type(JsonFieldType.STRING).description("한줄 평"),
                                        fieldWithPath("deadLine").type(JsonFieldType.STRING).description("반납기한"),
                                        fieldWithPath("author").type(JsonFieldType.STRING).description("저자명"),
                                        fieldWithPath("publisher").type(JsonFieldType.STRING).description("출판사"),
                                        fieldWithPath("publishDate").type(JsonFieldType.STRING).description("출판일"),
                                        fieldWithPath("returnLocation").type(JsonFieldType.STRING).description("반납 장소"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호")
                                )
                        ));

    }

    @Test
    @WithMockUser
    void searchContainerBookListTest() throws Exception {

        given(bookService.searchBookListByContainer("JPA", PageRequest.of(1, 5)))
                .willReturn(
                        ContainerBookInfo.builder()
                                .page(1)
                                .size(5)
                                .totalCount(10)
                                .bookList(
                                        List.of(ContainerBook.builder()
                                                .title("자바 ORM 표준 JPA 프로그래밍")
                                                .author("김영한 지음")
                                                .pubDate(LocalDate.of(2015, 07, 27))
                                                .description("도서 내용 설명")
                                                .imageUrl("https://image.aladin.co.kr/product/6268/14/cover150/8960777331_1.jpg")
                                                .categoryMapId(33)
                                                .bookCategory(BookCategory.COMPUTER_MOBILE)
                                                .publisher("에이콘출판")
                                                .build()
                                        )
                                )
                                .build());

        mockMvc.perform(get("/api/v1/books/container/search")
                        .param("searchKeyword", "JPA")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("searchKeyword").description("검색 키워드"),
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈(조회 갯수)")
                        ),
                        responseFields(

                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("사이즈(조회 갯수)"),
                                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("전체 조회된 도서 갯수"),
                                fieldWithPath("bookList.[].title").type(JsonFieldType.STRING).description("도서 제목"),
                                fieldWithPath("bookList.[].author").type(JsonFieldType.STRING).description("저자"),
                                fieldWithPath("bookList.[].pubDate").type(JsonFieldType.STRING).description("발행일"),
                                fieldWithPath("bookList.[].description").type(JsonFieldType.STRING).description("도서 설명"),
                                fieldWithPath("bookList.[].imageUrl").type(JsonFieldType.STRING).description("썸네일 이미지"),
                                fieldWithPath("bookList.[].categoryMapId").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
                                fieldWithPath("bookList.[].bookCategory").type(JsonFieldType.STRING).description("카테고리 이름"),
                                fieldWithPath("bookList.[].publisher").type(JsonFieldType.STRING).description("출판사")
                        )
                ));
    }
}