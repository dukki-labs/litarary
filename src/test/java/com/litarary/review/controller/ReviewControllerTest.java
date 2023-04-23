package com.litarary.review.controller;

import com.litarary.common.RestDocsControllerTest;
import com.litarary.common.util.MultiValueMapperUtils;
import com.litarary.review.controller.dto.ReviewDto;
import com.litarary.review.service.ReviewService;
import com.litarary.review.service.dto.PageingReviewInfo;
import com.litarary.review.service.dto.ReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends RestDocsControllerTest {

    private final String BASE_URI = "/api/v1";
    @MockBean
    private ReviewService reviewService;


    @Test
    void reviewTest() throws Exception {
        final String uri = BASE_URI + "/reviews";
        final ReviewDto.Request request = new ReviewDto.Request(1L, 1, 5);
        final MultiValueMap<String, String> params = MultiValueMapperUtils.convert(objectMapper, request);

        when(reviewService.findBookReviewList(any()))
                .thenReturn(
                        PageingReviewInfo.Response
                                .builder()
                                .page(1)
                                .size(5)
                                .totalCount(1)
                                .totalPage(1)
                                .last(true)
                                .reviewInfos(
                                        List.of(
                                                ReviewInfo
                                                        .builder()
                                                        .memberId(1L)
                                                        .bookId(1L)
                                                        .nickName("test")
                                                        .regReviewDateTime(LocalDateTime.now())
                                                        .updateReviewDateTime(LocalDateTime.now())
                                                        .build()
                                        )
                                )
                                .build()
                );
        mockMvc.perform(get(uri)
                        .requestAttr("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .params(params))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("bookId").description("도서 고유번호"),
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 사이즈")
                                ),
                                responseFields(
                                        fieldWithPath("page").description("페이지 번호"),
                                        fieldWithPath("size").description("페이지 사이즈"),
                                        fieldWithPath("totalCount").description("전체 리뷰 개수"),
                                        fieldWithPath("totalPage").description("전체 페이지 개수"),
                                        fieldWithPath("last").description("마지막 페이지 여부"),
                                        fieldWithPath("reviewInfoList[].memberId").description("회원 고유번호"),
                                        fieldWithPath("reviewInfoList[].bookId").description("도서 고유번호"),
                                        fieldWithPath("reviewInfoList[].nickName").description("회원 닉네임"),
                                        fieldWithPath("reviewInfoList[].review").description("회원 반납리뷰"),
                                        fieldWithPath("reviewInfoList[].regReviewDateTime").description("리뷰 등록일시"),
                                        fieldWithPath("reviewInfoList[].updateReviewDateTime").description("리뷰 수정일시")
                                )
                        )
                );
    }
}