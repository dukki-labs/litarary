package com.litarary.category.service.dto;

import com.litarary.book.domain.entity.Book;
import com.litarary.book.service.dto.BookInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PageBookInfo {

    private int page;
    private int size;
    private int totalPage;
    private long totalElements;
    private boolean last;
    private List<BookInfo> contents;

    public static PageBookInfo of(int page, int size, Page<Book> books) {
        List<BookInfo> bookInfos = books.getContent()
                .stream()
                .map(BookInfo::of)
                .toList();

        return PageBookInfo.builder()
                .page(page + 1)
                .size(size)
                .totalElements(books.getTotalElements())
                .totalPage(books.getTotalPages())
                .contents(bookInfos)
                .last(books.isLast())
                .build();
    }
}
