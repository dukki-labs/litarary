package com.litarary.book.service.dto;

import com.litarary.account.domain.BookCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ContainerBook {
    private String title;
    private String author;
    private LocalDate pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private String imageUrl;
    private long categoryMapId;
    private BookCategory bookCategory;
    private String publisher;
}
