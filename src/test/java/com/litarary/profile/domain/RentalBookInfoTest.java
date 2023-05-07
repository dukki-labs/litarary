package com.litarary.profile.domain;

import com.litarary.book.domain.RentalState;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.BookRental;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.domain.entity.DeadLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RentalBookInfoTest {

    private RentalBookInfo rentalBookInfo = new RentalBookInfo();
    @Test
    @DisplayName("대여기간이 만료되었는지 확인")
    void isRentalExpiredTest() {
        BookRental bookRental = BookRental.builder()
                .book(Book.builder()
                        .deadLine(DeadLine.ONE_WEEK)
                        .category(Category.builder().build())
                        .createdAt(LocalDateTime.now())
                        .build())
                .rentalState(RentalState.RENTAL)
                .rentalDateTime(LocalDateTime.now())
                .build();

        RentalBookInfo resultRentalBookInfo = rentalBookInfo.of(bookRental);
        assertThat(resultRentalBookInfo.isRentalExpired()).isFalse();
    }

    @Test
    @DisplayName("잔여일수 계산이 성공한다.")
    void calculateRemainDaysTest() {
        BookRental bookRental = BookRental.builder()
                .book(Book.builder()
                        .deadLine(DeadLine.ONE_WEEK)
                        .category(Category.builder().build())
                        .createdAt(LocalDateTime.now())
                        .build())
                .rentalState(RentalState.RENTAL)
                .rentalDateTime(LocalDateTime.now())
                .returnDateTime(LocalDateTime.now())
                .build();

        RentalBookInfo resultRentalBookInfo = rentalBookInfo.of(bookRental);
        assertThat(resultRentalBookInfo.getRemainDay()).isZero();
    }
}