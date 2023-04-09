package com.litarary.book.domain.entity;

import com.litarary.account.domain.UseYn;
import com.litarary.account.domain.entity.Company;
import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.service.dto.RegisterBook;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(value = EnumType.STRING)
    private RentalUseYn rentalUseYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Book(Member member, Category category, RegisterBook registerBook) {
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
        this.member = member;
        this.company = member.getCompany();
        this.rentalUseYn = RentalUseYn.Y;
    }

    public void updateRentalUseYn(RentalUseYn rentalUseYn) {
        this.rentalUseYn = rentalUseYn;
    }
    public void updateRecommendCount(int addCount) {
        this.recommendCount += addCount;
    }
}
