package com.litarary.book.controller.dto;

import com.litarary.account.domain.BookCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerBookInfoDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response {
        private int page;
        private int size;
        private int totalCount;

        private List<ContainerBookDto> bookList;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ContainerBookDto {
        private String title;
        private String author;
        private LocalDate pubDate;
        private String description;
        private String imageUrl;
        private long categoryMapId;
        private BookCategory bookCategory;
        private String publisher;
    }
}
