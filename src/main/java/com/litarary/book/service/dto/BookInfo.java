package com.litarary.book.service.dto;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.DeadLine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class BookInfo {

    private Long id;
    private String title;
    private String imageUrl;
    private String content;
    private String review;
    private DeadLine deadLine;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private String returnLocation;
    private int recommendCount;
    private LocalDateTime createdAt;
    private long categoryId;
    private BookCategory category;

    private NewTag newTag;

    public static BookInfo of(Book book) {
        return BookInfo.builder()
                .id(book.getId())
                .title(book.getTitle())
                .imageUrl(book.getImageUrl())
                .content(book.getContent())
                .review(book.getReview())
                .deadLine(book.getDeadLine())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishDate(book.getPublishDate())
                .returnLocation(book.getReturnLocation())
                .recommendCount(book.getRecommendCount())
                .categoryId(book.getCategory().getId())
                .category(book.getCategory().getBookCategory())
                .createdAt(book.getCreatedAt())
                .newTag(NewTag.isNewTag(book.getCreatedAt()))
                .build();
    }
}
