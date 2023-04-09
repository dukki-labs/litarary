package com.litarary.book.controller.dto;


import lombok.*;

import javax.validation.constraints.Max;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookReturnDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        private String rentalReview;
        @Max(1)
        private int recommend;
    }
}
