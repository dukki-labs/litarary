package com.litarary.review.controller.dto;

import com.litarary.review.service.dto.ReviewInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public class ReviewDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        private long bookId;
        @Min(1)
        private int page;
        @Min(5)
        @Max(10)
        private int size;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int page;
        private int size;
        private int totalCount;
        private int totalPage;
        private boolean last;
        private List<ReviewInfo> reviewInfoList;
    }
}
