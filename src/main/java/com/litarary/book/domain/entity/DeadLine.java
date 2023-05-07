package com.litarary.book.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeadLine {
    ONE_WEEK(7),
    TWO_WEEK(14),
    THREE_WEEK(21),
    FOUR_WEEK(28);
    private final int days;

    public int getDays() {
        return days;
    }
}
