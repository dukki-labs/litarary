package com.litarary.book.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecentBookDto {

    @Getter
    @Builder
    public static class Response {
        private List<BookInfoDto> bookInfoDtoList;
    }
}
