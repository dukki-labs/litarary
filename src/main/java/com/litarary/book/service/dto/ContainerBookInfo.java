package com.litarary.book.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ContainerBookInfo {

    private int page;
    private int size;
    private int totalCount;
    private String searchKeyword;
    private List<ContainerBook> bookList;
}
