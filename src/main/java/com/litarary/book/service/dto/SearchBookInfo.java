package com.litarary.book.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchBookInfo {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private long memberId;
        private String searchWord;
        private int page;
        private int size;
    }

    @Builder
    @Getter
    public static class Response {
        private int page;
        private int size;
        private int totalCount;
        private int totalPage;
        private boolean last;
        private List<BookContent> bookContentList;
    }

}
