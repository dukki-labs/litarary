package com.litarary.account.domain.entity;

import com.litarary.account.domain.BookCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookCategory bookCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    public void updateMember(Member member) {
        this.memberId = member;
    }

    public void updateTypeAndMember(BookCategory bookCategory, Member member) {
        this.bookCategory = bookCategory;
        this.memberId = member;
    }
}
