package com.litarary.book.domain.entity;

import com.litarary.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private RentalReview(String review, Book book) {
        this.review = review;
        this.book = book;
    }
    public static RentalReview createRentalReview(String review, Book book) {
        return new RentalReview(review, book);
    }
}
