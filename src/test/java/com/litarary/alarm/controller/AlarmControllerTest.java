package com.litarary.alarm.controller;

import com.litarary.alarm.controller.dto.AcceptBookDto;
import com.litarary.alarm.domain.AcceptBookInfo;
import com.litarary.alarm.domain.AcceptState;
import com.litarary.alarm.domain.AlarmBookInfo;
import com.litarary.alarm.service.AlarmService;
import com.litarary.book.domain.RentalState;
import com.litarary.common.RestDocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AlarmControllerTest extends RestDocsControllerTest {
    private final String BASE_URL = "/api/v1/alarms";
    @MockBean
    private AlarmService alarmService;

    @Test
    void alarmListTest() throws Exception {
        final String uri = BASE_URL + "/list";

        given(alarmService.bookRentalTargetList(anyLong()))
                .willReturn(
                        List.of(
                                AlarmBookInfo.builder()
                                        .bookRentalId(1L)
                                        .rentalState(RentalState.RENTAL)
                                        .bookUrl("http://test.com")
                                        .memberId(1L)
                                        .bookId(1L)
                                        .title("test")
                                        .nickName("닉네임")
                                        .build())
                );

        mockMvc.perform(get(uri).requestAttr("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].bookRentalId").type(JsonFieldType.NUMBER).description("도서 대여 고유번호"),
                                        fieldWithPath("[].rentalState").type(JsonFieldType.STRING).description("도서 상태 \n" +
                                                "`[RENTAL: 대여]` \n, " +
                                                "`[RETURN: 반납]` \n, " +
                                                "`[REQUEST: 요청]`"),
                                        fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("[].bookId").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                        fieldWithPath("[].bookUrl").type(JsonFieldType.STRING).description("도서 이미지 URL"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("도서 제목")
                                )
                        )
                );
    }

    @Test
    void acceptBookRentalTest() throws Exception {
        final String uri = BASE_URL + "/accept/books";

        doNothing().when(alarmService).acceptBookRental(any(AcceptBookInfo.class));
        AcceptBookDto acceptBookDto = new AcceptBookDto(1L, AcceptState.ACCEPT);
        String requestBody = objectMapper.writeValueAsString(acceptBookDto);

        mockMvc.perform(post(uri)
                        .requestAttr("memberId", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("도서 고유번호"),
                                        fieldWithPath("acceptState").type(JsonFieldType.STRING).description("도서 상태 \n" +
                                                "`[ACCEPT: 수락]` \n, " +
                                                "`[REFUSE: 거절]` \n, ")
                                )
                        )
                );
    }
}