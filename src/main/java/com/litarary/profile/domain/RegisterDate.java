package com.litarary.profile.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RegisterDate {
    ONE_MONTH("1개월"),
    THREE_MONTH("3개월"),
    SIX_MONTH("6개월"),
    NONE("전체");

    private final String hint;
}
