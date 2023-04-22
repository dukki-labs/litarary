package com.litarary.recommend.controller.dto;

import lombok.Builder;
import lombok.Getter;

public class RecommendDto {
    @Getter
    @Builder
    public static class Response {
        private int recommendCount;
    }
}
