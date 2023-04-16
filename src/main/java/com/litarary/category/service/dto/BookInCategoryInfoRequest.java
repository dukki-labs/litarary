package com.litarary.category.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookInCategoryInfoRequest {

    private long memberId;
    private long categoryId;
    private int page;
    private int size;
}
