package com.litarary.book.controller.dto;

import com.litarary.book.service.dto.BookContent;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchBookDto {
    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private String keyWord;
        @Min(1)
        private int page;
        @Min(6)
        @Max(10)
        private int size;
    }

    @Getter
    @Builder
    public static class Response {
        private int page;
        private int size;
        private int totalPage;
        private int totalCount;
        private boolean last;
        private List<BookContent> bookInfoList;
    }
}
