package com.litarary.book.controller.dto;

import com.litarary.account.domain.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoDto {
    private String imageUrl;
    private String title;

    private BookCategory bookCategory;
    private String content;
    private int likeCount;
    private int viewCount;

    private LocalDateTime regDt;

}
