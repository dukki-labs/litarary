package com.litarary.book.service.dto;

import com.litarary.book.domain.entity.DeadLine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class RegisterBook {
    private String title;
    private String imageUrl;
    private String content;
    private String review;
    private DeadLine deadLine;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private String returnLocation;
    private Long categoryId;
    private Long memberId;
}
