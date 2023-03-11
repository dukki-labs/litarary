package com.litarary.book.service.aladdin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class AladdinBookDto {
    private String title;
    private String link;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private String cover;
    private long categoryId;
    private String categoryName;
    private String publisher;
}
