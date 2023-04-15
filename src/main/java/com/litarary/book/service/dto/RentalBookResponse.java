package com.litarary.book.service.dto;

import com.litarary.account.domain.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalBookResponse {
    private Long id;
    private String author;
    private String content;
    private LocalDateTime createdAt;
    private String deadLine;
    private String imageUrl;
    private LocalDate publishDate;
    private String publisher;
    private Integer recommendCount;
    private String returnLocation;
    private String review;
    private String title;
    private LocalDateTime updatedAt;
    private Long categoryId;
    private BookCategory bookCategory;
    private Long companyId;
    private Long memberId;
    private String rentalUseYn;
    private NewTag newTag;

    public NewTag getNewTag() {
        return NewTag.isNewTag(createdAt);
    }
}
