package com.litarary.book.service.aladdin.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class AladdinBookRequest {
    private String secretKey;
    private String Query;
    private String QueryType;
    private String SearchTarget;
    private int Start;
    private int MaxResults;
    private String Sort;
    private String Output;
    private String Cover;

    public static AladdinBookRequest createRequest(String secretKey, String keyword, Pageable pageable) {
        return AladdinBookRequest.builder()
                .secretKey(secretKey)
                .Query(keyword)
                .QueryType("Title")
                .SearchTarget("Book")
                .Start(pageable.getPageNumber())
                .MaxResults(pageable.getPageSize())
                .Sort("Accuracy")
                .Output("JS")
                .Cover("MidBig")
                .build();
    }
}
