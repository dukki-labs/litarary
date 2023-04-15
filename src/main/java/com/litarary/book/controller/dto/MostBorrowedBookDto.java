package com.litarary.book.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

public class MostBorrowedBookDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @Min(1)
        private int page;

        @Size(min = 1, max = 6)
        private int size;
    }

    @Getter
    @Builder
    public static class Response {

        private List<BookInfoDto> bookInfoDtoList;
    }
}
