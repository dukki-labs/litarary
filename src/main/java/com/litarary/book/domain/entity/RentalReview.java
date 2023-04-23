package com.litarary.book.domain.entity;

import com.litarary.account.domain.entity.Member;
import com.litarary.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private RentalReview(String review, Book book, Member member) {
        this.review = review;
        this.book = book;
        this.member = member;
    }
    public static RentalReview createRentalReview(String review, Book book, Member member) {
        return new RentalReview(review, book, member);
    }
}
