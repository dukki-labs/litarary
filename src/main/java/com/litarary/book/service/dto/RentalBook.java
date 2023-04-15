package com.litarary.book.service.dto;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.SearchType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RentalBook {
    private SearchType searchType;
    private BookCategory bookCategory;
    private String searchKeyword;
    private int page;
    private int size;
}
