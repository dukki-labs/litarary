package com.litarary.review.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageingReviewInfo {

    @Getter
    @Builder
    public static class Request {
        private long memberId;
        private long bookId;
        private int page;
        private int size;
    }

    @Getter
    @Builder
    public static class Response {
        private int page;
        private int size;
        private int totalCount;
        private int totalPage;
        private boolean last;
        private List<ReviewInfo> reviewInfos;
    }
}
