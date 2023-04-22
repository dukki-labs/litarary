package com.litarary.recommend.domain.entity;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public RecommendBook(Member member, Book book) {
        this.member = member;
        this.book = book;
    }
}
