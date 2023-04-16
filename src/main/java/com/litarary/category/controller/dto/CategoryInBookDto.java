package com.litarary.category.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CategoryInBookDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @Min(1)
        private int page;
        @Max(3)
        private int size;
    }
}
