package com.litarary.book.controller.dto;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.SearchType;
import com.litarary.book.service.dto.RentalBookResponse;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RentalBookDto {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        @NotNull
        private SearchType searchType;
        private BookCategory bookCategory;
        private String searchKeyword;
        @Min(1)
        private int page;
        @Size(min = 1, max = 50)
        private int size;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        List<RentalBookResponse> rentalBookResponseList;
    }
}
