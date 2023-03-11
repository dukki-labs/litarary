package com.litarary.common.error;

import com.litarary.account.controller.dto.MemberLoginDto;
import com.litarary.common.RestDocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ErrorMessageTest extends RestDocsControllerTest {

    @Test
    void requestParameterValidTest() throws Exception {
        MemberLoginDto.Request request = MemberLoginDto.Request.builder()
                .email(null)
                .password(null)
                .build();
        String requestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/account/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(restDocs.document(
                   responseFields(
                           fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태"),
                           fieldWithPath("errorCode").type(JsonFieldType.STRING).description("에러 코드"),
                           fieldWithPath("errorMessage").type(JsonFieldType.STRING).description("에러 메시지"),
                           fieldWithPath("detailErrorMessage").type(JsonFieldType.STRING).description("에러 상세 메시지").optional(),
                           fieldWithPath("errorFields.[].fieldName").type(JsonFieldType.STRING).description("필드 명"),
                           fieldWithPath("errorFields.[].message").type(JsonFieldType.STRING).description("필드 에러 메시지")
                   )
                ));
    }
}
