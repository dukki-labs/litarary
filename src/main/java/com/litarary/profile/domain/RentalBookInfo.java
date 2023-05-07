package com.litarary.profile.domain;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.RentalState;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.BookRental;
import com.litarary.book.domain.entity.DeadLine;
import com.litarary.book.service.dto.NewTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalBookInfo {
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
    private RentalUseYn rentalUseYn;
    private LocalDateTime createdAt;
    private Long categoryId;
    private BookCategory category;
    private NewTag newTag;
    private boolean rentalExpired;
    private RentalState rentalState;
    private LocalDateTime rentalDateTime;
    private LocalDateTime returnDateTime;
    private int remainDay;

    public RentalBookInfo of(BookRental bookRental) {
        final RentalState state = bookRental.getRentalState();
        final Book book = bookRental.getBook();
        final boolean expired = isRentalExpired(book.getDeadLine(), state, bookRental.getRentalDateTime());
        final int remainDays = calculateRemainDays(book.getDeadLine(), bookRental.getRentalDateTime(), bookRental.getReturnDateTime());

        return RentalBookInfo.builder()
                .id(book.getId())
                .title(book.getTitle())
                .imageUrl(book.getImageUrl())
                .content(book.getContent())
                .review(book.getReview())
                .deadLine(book.getDeadLine())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishDate(book.getPublishDate())
                .rentalUseYn(book.getRentalUseYn())
                .returnLocation(book.getReturnLocation())
                .categoryId(book.getCategory().getId())
                .category(book.getCategory().getBookCategory())
                .createdAt(book.getCreatedAt())
                .newTag(NewTag.isNewTag(book.getCreatedAt()))
                .rentalExpired(expired)
                .rentalState(state)
                .rentalDateTime(bookRental.getRentalDateTime())
                .returnDateTime(bookRental.getReturnDateTime())
                .remainDay(remainDays)
                .build();
    }

    private boolean isRentalExpired(DeadLine deadLine,
                                    RentalState rentalState,
                                    LocalDateTime rentalDateTime) {

        if (rentalState.isRental()) {
            LocalDateTime expiredDateTime = rentalDateTime.plusDays(deadLine.getDays());
            return LocalDateTime.now().isAfter(expiredDateTime);
        }
        return false;
    }

    public int calculateRemainDays(DeadLine deadLine,
                                   LocalDateTime rentalDateTime,
                                   LocalDateTime returnDateTime) {
        if (returnDateTime != null) {
            return (int) Duration.between(rentalDateTime, returnDateTime).toDays();
        }

        LocalDateTime expiredDateTime = rentalDateTime.plusDays(deadLine.getDays());
        return (int) Duration.between(LocalDateTime.now(), expiredDateTime).toDays();
    }
}
