package com.litarary.book.domain.entity;

import com.litarary.account.domain.UseYn;
import com.litarary.book.service.dto.RegisterBook;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String imageUrl;
    private String content;
    private String review;
    @Enumerated(value = EnumType.STRING)
    private DeadLine deadLine;
    @Enumerated(value = EnumType.STRING)
    private UseYn useYn;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private String returnLocation;
    private int recommendCount;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    public Book(Category category, RegisterBook registerBook) {
        this.title = registerBook.getTitle();
        this.imageUrl = registerBook.getImageUrl();
        this.content = registerBook.getContent();
        this.review = registerBook.getReview();
        this.deadLine = registerBook.getDeadLine();
        this.useYn = UseYn.Y;
        this.author = registerBook.getAuthor();
        this.publisher = registerBook.getPublisher();
        this.publishDate = registerBook.getPublishDate();
        this.returnLocation = registerBook.getReturnLocation();
        this.recommendCount = 0;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.category = category;
    }
}
