package com.litarary.recommend.controller;

import com.litarary.common.RestDocsControllerTest;
import com.litarary.recommend.service.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecommendControllerTest extends RestDocsControllerTest {

    private final String BASE_URI = "/api/v1/recommend";

    @MockBean
    private RecommendService recommendService;

    @Test
    void recommendTest() throws Exception {
        final String uri = BASE_URI + "/books/{bookId}";

        when(recommendService.updateRecommendCount(anyLong(), anyLong(), any())).thenReturn(1);

        mockMvc.perform(post(uri, 1L)
                        .requestAttr("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("recommendStatus", "NONE"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("bookId").description("도서 고유번호")
                                ),
                                requestParameters(
                                        parameterWithName("recommendStatus").description("추천 상태")
                                ),
                                responseFields(
                                        fieldWithPath("recommendCount").type(JsonFieldType.NUMBER).description("추천 수")
                                )
                        )
                );
    }
}