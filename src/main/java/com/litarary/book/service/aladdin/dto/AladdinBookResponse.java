package com.litarary.book.service.aladdin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Getter
public class AladdinBookResponse {
    private String link;
    private int totalResults;
    private int startIndex;
    private int itemsPerPage;
    private String query;
    private int searchCategoryId;
    private String searchCategoryName;

    private List<AladdinBookDto> item;
}
