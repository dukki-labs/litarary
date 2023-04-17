package com.litarary.book.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageBookContent {
    private int page;
    private int size;
    private int totalCount;
    private int totalPage;
    private boolean last;
    private List<BookContent> bookContentList;
}
