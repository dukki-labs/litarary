package com.litarary.book.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.controller.dto.BookRegistrationDto;
import com.litarary.book.controller.dto.BookReturnDto;
import com.litarary.book.controller.dto.RentalBookDto;
import com.litarary.book.domain.SearchType;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.domain.entity.DeadLine;
import com.litarary.book.service.BookService;
import com.litarary.book.service.dto.*;
import com.litarary.common.RestDocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
        final long memberId = 1;
        final int size = 10;
        BookInfo response = createDummyBookInfo();
        given(bookService.recentBookList(anyLong(), anyInt())).willReturn(List.of(response));

        mockMvc.perform(get("/api/v1/recent/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("memberId", String.valueOf(memberId))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("memberId").description("회원 고유번호"),
                                parameterWithName("size").description("책 조회 갯수")
                        ),
                        responseFields(
                                fieldWithPath("bookInfoDtoList[].id").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                fieldWithPath("bookInfoDtoList[].imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                fieldWithPath("bookInfoDtoList[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("bookInfoDtoList[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 고유번호"),
                                fieldWithPath("bookInfoDtoList[].category").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("bookInfoDtoList[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("bookInfoDtoList[].recommendCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("bookInfoDtoList[].author").type(JsonFieldType.STRING).description("저자"),
                                fieldWithPath("bookInfoDtoList[].review").type(JsonFieldType.STRING).description("리뷰"),
                                fieldWithPath("bookInfoDtoList[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("bookInfoDtoList[].publishDate").type(JsonFieldType.STRING).description("출판일"),
                                fieldWithPath("bookInfoDtoList[].deadLine").type(JsonFieldType.STRING).description("대출 기한"),
                                fieldWithPath("bookInfoDtoList[].returnLocation").type(JsonFieldType.STRING).description("반납 위치"),
                                fieldWithPath("bookInfoDtoList[].newTag").type(JsonFieldType.STRING).description("[신규: NEW] \n [일반도서: NORMAL]"),
                                fieldWithPath("bookInfoDtoList.[].regDt").type(JsonFieldType.STRING).description("등록 날짜")
                        )
                ));
    }

//    @Test
//    @WithMockUser
//    void concernBookListTest() throws Exception {
//
//        mockMvc.perform(get("/api/v1/books/concern")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .param("inquiryDate", "2023-02-23"))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document(
//                        requestParameters(
//                                parameterWithName("inquiryDate").description("조회 요청 날짜")
//                        ),
//                        responseFields(
//                                fieldWithPath("concernBookTypeDto.[].bookCategory").type(JsonFieldType.STRING).description("카테고리"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].imageUrl")
//                                        .type(JsonFieldType.STRING).description("도서 썸네일 URL"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].title")
//                                        .type(JsonFieldType.STRING).description("제목"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].bookCategory")
//                                        .type(JsonFieldType.STRING).description("카테고리"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].content")
//                                        .type(JsonFieldType.STRING).description("본문 내용"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].likeCount")
//                                        .type(JsonFieldType.NUMBER).description("좋아요 갯수"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].viewCount")
//                                        .type(JsonFieldType.NUMBER).description("조회수"),
//                                fieldWithPath("concernBookTypeDto.[].bookInfoDtoList.[].regDt")
//                                        .type(JsonFieldType.STRING).description("등록 날짜")
//                        )
//                ));
//    }

    @Test
    @WithMockUser
    void mostBorrowedBookListTest() throws Exception {
        final String uri = BASE_URI + "/books/most-borrowed";

        when(bookService.mostBorrowedBookList(anyLong(), any())).thenReturn(
                List.of(createDummyBookInfo())
        );

        mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("page", "1")
                        .param("size", "6")
                .requestAttr("memberId", 1L))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지당 조회 갯수")
                                ),
                                responseFields(
                                        fieldWithPath("bookInfoDtoList[].id").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                        fieldWithPath("bookInfoDtoList[].imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                        fieldWithPath("bookInfoDtoList[].title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("bookInfoDtoList[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 고유번호"),
                                        fieldWithPath("bookInfoDtoList[].category").type(JsonFieldType.STRING).description("카테고리"),
                                        fieldWithPath("bookInfoDtoList[].content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("bookInfoDtoList[].recommendCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                        fieldWithPath("bookInfoDtoList[].author").type(JsonFieldType.STRING).description("저자"),
                                        fieldWithPath("bookInfoDtoList[].review").type(JsonFieldType.STRING).description("리뷰"),
                                        fieldWithPath("bookInfoDtoList[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                        fieldWithPath("bookInfoDtoList[].publishDate").type(JsonFieldType.STRING).description("출판일"),
                                        fieldWithPath("bookInfoDtoList[].deadLine").type(JsonFieldType.STRING).description("대출 기한"),
                                        fieldWithPath("bookInfoDtoList[].returnLocation").type(JsonFieldType.STRING).description("반납 위치"),
                                        fieldWithPath("bookInfoDtoList[].newTag").type(JsonFieldType.STRING).description("[신규: NEW] \n [일반도서: NORMAL]"),
                                        fieldWithPath("bookInfoDtoList[].regDt").type(JsonFieldType.STRING).description("등록 날짜")
                                )
                        )
                );
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

    @Test
    @WithMockUser
    void bookRentalTest() throws Exception {
        doNothing().when(bookService).rentalBook(anyLong(), anyLong());
        final String uri = BASE_URI + "/books/{bookId}/rental";
        final long memberId = 1L;

        mockMvc.perform(post(uri, 1)
                        .requestAttr("memberId", memberId))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("bookId").description("도서 고유번호")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    void findBookReturnTest() throws Exception {
        final String uri = BASE_URI + "/books/return";
        final long memberId = 1L;
        when(bookService.findBookReturn(memberId)).thenReturn(
                ReturnBook.Response.builder()
                        .memberId(1L)
                        .providerNickName("도서 제공자 닉네임")
                        .bookUrl("http://testwefwef.com")
                        .title("도서 제목")
                        .publisher("출판사")
                        .category(
                                Category.builder()
                                        .id(1L)
                                        .bookCategory(BookCategory.COMPUTER_MOBILE)
                                        .build()
                        )
                        .content("도서 내용")
                        .deadLine(DeadLine.ONE_WEEK)
                        .returnLocation("반납 장소")
                        .review("한줄 평")
                        .newTag(NewTag.NEW)
                        .build()
        );

        mockMvc.perform(get(uri)
                        .requestAttr("memberId", memberId))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("providerNickName").type(JsonFieldType.STRING).description("도서 제공자 닉네임"),
                                        fieldWithPath("bookUrl").type(JsonFieldType.STRING).description("도서 이미지 URL"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("도서 제목"),
                                        fieldWithPath("publisher").type(JsonFieldType.STRING).description("출판사"),
                                        fieldWithPath("category.id").type(JsonFieldType.NUMBER).description("카테고리 고유번호"),
                                        fieldWithPath("category.bookCategory").type(JsonFieldType.STRING).description("카테고리"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("도서 설명"),
                                        fieldWithPath("deadLine").type(JsonFieldType.STRING).description("대여기간"),
                                        fieldWithPath("returnLocation").type(JsonFieldType.STRING).description("대여 반납장소"),
                                        fieldWithPath("review").type(JsonFieldType.STRING).description("전달 내용"),
                                        fieldWithPath("newTag").type(JsonFieldType.STRING).description("[신규: NEW] \n [일반도서: NORMAL]")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    void bookReturnTest() throws Exception {
        final String uri = BASE_URI + "/books/return";
        final BookReturnDto.Request requestDto = BookReturnDto.Request.builder()
                .recommend(1)
                .rentalReview("대여 후기")
                .build();
        doNothing().when(bookService).returnBook(anyLong(), any());

        mockMvc.perform(post(uri)
                        .requestAttr("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("recommend").type(JsonFieldType.NUMBER).description("추천 [추천한다: 1] \n [추천하지 않는다 : 0]"),
                                        fieldWithPath("rentalReview").type(JsonFieldType.STRING).description("대여 후기")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    void rentalBookListTest() throws Exception {
        final String uri = BASE_URI + "/books/rentals";
        RentalBookDto.Request requestDto = RentalBookDto.Request
                .builder()
                .searchType(SearchType.NEW)
                .bookCategory(BookCategory.COMPUTER_MOBILE)
                .searchKeyword("키워드")
                .page(1)
                .size(10)
                .build();
        String request = objectMapper.writeValueAsString(requestDto);

        when(bookService.findRentalBookList(anyLong(), any())).thenReturn(
                List.of(
                        RentalBookResponse.builder()
                                .id(1L)
                                .title("도서 제목")
                                .publisher("출판사")
                                .publishDate(LocalDate.now())
                                .recommendCount(1)
                                .categoryId(1L)
                                .bookCategory(BookCategory.COMPUTER_MOBILE)
                                .content("도서 설명")
                                .deadLine("대여기간")
                                .returnLocation("대여 반납장소")
                                .review("전달 내용")
                                .newTag(NewTag.NEW)
                                .imageUrl("http://testImage.com")
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .author("저자")
                                .memberId(1L)
                                .companyId(1L)
                                .rentalUseYn("대여 가능 여부")
                                .build())
        );
        mockMvc.perform(get(uri)
                        .requestAttr("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("searchType").type(JsonFieldType.STRING).description("검색 타입 [신규: NEW] \n [추천: RECOMMEND]"),
                                        fieldWithPath("bookCategory").type(JsonFieldType.STRING).description("관심 카테고리 \n\n" +
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
                                                "`[OTHER:기타]`"),
                                        fieldWithPath("searchKeyword").type(JsonFieldType.STRING).description("검색 키워드"),
                                        fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지"),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("사이즈(조회 갯수)")
                                ),
                                responseFields(
                                        fieldWithPath("rentalBookResponseList[].id").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                        fieldWithPath("rentalBookResponseList[].title").type(JsonFieldType.STRING).description("도서 제목"),
                                        fieldWithPath("rentalBookResponseList[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                        fieldWithPath("rentalBookResponseList[].publishDate").type(JsonFieldType.STRING).description("출판일"),
                                        fieldWithPath("rentalBookResponseList[].recommendCount").type(JsonFieldType.NUMBER).description("추천 갯수"),
                                        fieldWithPath("rentalBookResponseList[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 고유번호"),
                                        fieldWithPath("rentalBookResponseList[].bookCategory").type(JsonFieldType.STRING).description("카테고리"),
                                        fieldWithPath("rentalBookResponseList[].content").type(JsonFieldType.STRING).description("도서 설명"),
                                        fieldWithPath("rentalBookResponseList[].deadLine").type(JsonFieldType.STRING).description("대여기간"),
                                        fieldWithPath("rentalBookResponseList[].returnLocation").type(JsonFieldType.STRING).description("대여 반납장소"),
                                        fieldWithPath("rentalBookResponseList[].review").type(JsonFieldType.STRING).description("전달 내용"),
                                        fieldWithPath("rentalBookResponseList[].newTag").type(JsonFieldType.STRING).description("[신규: NEW] \n [일반도서: NORMAL]"),
                                        fieldWithPath("rentalBookResponseList[].imageUrl").type(JsonFieldType.STRING).description("도서 이미지 URL"),
                                        fieldWithPath("rentalBookResponseList[].createdAt").type(JsonFieldType.STRING).description("도서 등록일"),
                                        fieldWithPath("rentalBookResponseList[].updatedAt").type(JsonFieldType.STRING).description("도서 수정일"),
                                        fieldWithPath("rentalBookResponseList[].author").type(JsonFieldType.STRING).description("저자"),
                                        fieldWithPath("rentalBookResponseList[].memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("rentalBookResponseList[].companyId").type(JsonFieldType.NUMBER).description("회사 고유번호"),
                                        fieldWithPath("rentalBookResponseList[].rentalUseYn").type(JsonFieldType.STRING).description("대여 가능 여부 [Y: 대여가능] [N: 대여불가]")
                                )

                        ));
    }

    private BookInfo createDummyBookInfo() {
        return BookInfo.builder()
                .id(1L)
                .imageUrl("test@image.com")
                .title("JPA마스터")
                .author("김영한")
                .categoryId(1)
                .category(BookCategory.COMPUTER_MOBILE)
                .content("도서 설명")
                .recommendCount(24)
                .review("올해 읽은 도서 중 가장 재미있었어요")
                .publisher("에이콘출판사")
                .publishDate(LocalDate.now())
                .deadLine(DeadLine.ONE_WEEK)
                .returnLocation("출입문앞 1층")
                .createdAt(LocalDateTime.now())
                .newTag(NewTag.NEW)
                .build();
    }
}