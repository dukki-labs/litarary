package com.litarary.book.controller.dto;


import com.litarary.book.domain.entity.DeadLine;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookRegistrationDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Request {
        @NotBlank
        private String title;
        private String imageUrl;
        @NotBlank
        private String content;
        private String review;
        @NotNull
        private DeadLine deadLine;
        @NotBlank
        private String author;
        @NotBlank
        private String publisher;
        @NotNull
        private LocalDate publishDate;
        @NotBlank
        private String returnLocation;
    }
}
