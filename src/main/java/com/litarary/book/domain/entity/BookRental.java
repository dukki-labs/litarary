package com.litarary.book.domain.entity;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.RentalState;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BookRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RentalState rentalState;

    @Column(nullable = false)
    private LocalDateTime rentalDateTime;
    private LocalDateTime returnDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public static BookRental createDefaultRental(Member member, Book book) {
        return BookRental.builder()
                .rentalState(RentalState.RENTAL)
                .rentalDateTime(LocalDateTime.now())
                .member(member)
                .book(book)
                .build();
    }

    public void updateRentalInfo() {
        this.returnDateTime = LocalDateTime.now();
        this.rentalState = RentalState.RETURN;
    }
}
