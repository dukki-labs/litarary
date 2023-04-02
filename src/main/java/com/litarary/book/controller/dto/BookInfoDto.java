package com.litarary.book.controller.dto;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.DeadLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoDto {
    private String imageUrl;
    private String title;

    private String author;
    private long categoryId;
    private BookCategory category;
    private String content;
    private int recommendCount;
    private String review;
    private String publisher;
    private LocalDate publishDate;
    private DeadLine deadLine;
    private String returnLocation;
    private LocalDateTime regDt;

}
