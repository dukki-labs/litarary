package com.litarary.category.controller.dto;

import com.litarary.book.domain.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class InterestCategoryDto {

    @Getter
    @Builder
    public static class Response {
        private long memberId;
        private String memberName;
        private List<Category> categoryList;
    }
}
