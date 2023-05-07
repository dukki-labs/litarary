package com.litarary.common.dummy;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.RentalState;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.DeadLine;
import com.litarary.book.service.dto.NewTag;
import com.litarary.profile.domain.RentalBookInfo;
import com.litarary.profile.domain.RentalBookPageInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DummyRentalBookPageInfo {
    public static RentalBookPageInfo of() {
        return RentalBookPageInfo.builder()
                .page(1)
                .size(10)
                .totalPage(1)
                .totalElements(1)
                .last(true)
                .contents(List.of(
                        RentalBookInfo.builder()
                                .id(1L)
                                .title("title")
                                .imageUrl("imageUrl")
                                .content("content")
                                .review("review")
                                .deadLine(DeadLine.ONE_WEEK)
                                .author("author")
                                .publisher("publisher")
                                .publishDate(LocalDate.now())
                                .returnLocation("returnLocation")
                                .rentalUseYn(RentalUseYn.Y)
                                .createdAt(LocalDateTime.now())
                                .categoryId(1L)
                                .category(BookCategory.COMPUTER_MOBILE)
                                .newTag(NewTag.NEW)
                                .rentalExpired(false)
                                .rentalState(RentalState.RETURN)
                                .rentalDateTime(LocalDateTime.now())
                                .returnDateTime(LocalDateTime.now())
                                .remainDay(0)
                                .build()
                ))
                .build();
    }
}
