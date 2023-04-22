package com.litarary.recommend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendStatus {
    INCREAMENT(1,"추천 추가"),
    DECREAMENT(-1,"추천 차감"),
    NONE(0, "추천 건너뛰기");
    private final int addCount;
    private final String hint;
}
