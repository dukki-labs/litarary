package com.litarary.profile.domain;

import com.litarary.book.domain.entity.BookRental;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Getter
@Builder
public class RentalBookPageInfo {
    private int page;
    private int size;
    private int totalPage;
    private long totalElements;
    private boolean last;
    private List<RentalBookInfo> contents;

    public static RentalBookPageInfo of(PageRequest pageRequest, Page<BookRental> bookRentals) {

        final int page = pageRequest.getPageNumber() + 1;
        final int size = pageRequest.getPageSize();
        List<RentalBookInfo> rentalBookInfos = bookRentals.stream()
                .map(book -> {
                    RentalBookInfo rentalBookInfo = new RentalBookInfo();
                    return rentalBookInfo.of(book);
                })
                .toList();

        return RentalBookPageInfo.builder()
                .page(page)
                .size(size)
                .totalElements(bookRentals.getTotalElements())
                .totalPage(bookRentals.getTotalPages())
                .contents(rentalBookInfos)
                .last(bookRentals.isLast())
                .build();
    }
}
