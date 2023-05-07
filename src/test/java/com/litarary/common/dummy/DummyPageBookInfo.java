package com.litarary.common.dummy;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.DeadLine;
import com.litarary.book.service.dto.BookInfo;
import com.litarary.book.service.dto.NewTag;
import com.litarary.category.service.dto.PageBookInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DummyPageBookInfo {

    public static PageBookInfo of() {
        return PageBookInfo.builder()
                .page(1)
                .size(3)
                .contents(List.of(
                        BookInfo.builder()
                                .id(1L)
                                .imageUrl("test@image.com")
                                .title("JPA마스터")
                                .author("김영한")
                                .categoryId(1)
                                .category(BookCategory.COMPUTER_MOBILE)
                                .content("도서 설명")
                                .recommendCount(24)
                                .review("올해 읽은 도서 중 가장 재미있었어요")
                                .rentalUseYn(RentalUseYn.Y)
                                .publisher("에이콘출판사")
                                .publishDate(LocalDate.now())
                                .deadLine(DeadLine.ONE_WEEK)
                                .returnLocation("출입문앞 1층")
                                .createdAt(LocalDateTime.now())
                                .newTag(NewTag.NEW)
                                .build()
                ))
                .totalElements(1)
                .totalPage(1)
                .last(true)
                .build();
    }
}
