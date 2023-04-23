package com.litarary.book.service.dto;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.Book;
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
public class BookContent {
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

    public static BookContent of(Book book) {
        return BookContent.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .content(book.getContent())
                .createdAt(book.getCreatedAt())
                .deadLine(book.getDeadLine().name())
                .imageUrl(book.getImageUrl())
                .publishDate(book.getPublishDate())
                .publisher(book.getPublisher())
                .recommendCount(book.getRecommendCount())
                .returnLocation(book.getReturnLocation())
                .review(book.getReview())
                .title(book.getTitle())
                .updatedAt(book.getUpdatedAt())
                .categoryId(book.getCategory().getId())
                .bookCategory(book.getCategory().getBookCategory())
                .companyId(book.getCompany().getId())
                .memberId(book.getMember().getId())
                .rentalUseYn(book.getRentalUseYn().name())
                .newTag(NewTag.isNewTag(book.getCreatedAt()))
                .build();
    }

    public NewTag getNewTag() {
        return NewTag.isNewTag(createdAt);
    }
}
