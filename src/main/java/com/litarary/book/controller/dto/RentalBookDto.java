package com.litarary.book.controller.dto;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.SearchType;
import com.litarary.book.service.dto.BookContent;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RentalBookDto {
    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private SearchType searchType;
        private BookCategory bookCategory;
        private String searchKeyword;
        @Min(1)
        private int page;
        @Min(1)
        @Max(50)
        private int size;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private int page;
        private int size;
        private int totalCount;
        private int totalPage;
        private boolean last;
        List<BookContent> rentalBookResponseList;
    }
}
