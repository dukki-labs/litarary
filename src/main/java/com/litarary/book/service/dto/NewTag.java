package com.litarary.book.service.dto;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public enum NewTag {
    NEW("신규도서"),
    DEFAULT("일반도서");

    private final String hint;

    public static NewTag isNewTag(LocalDateTime createdAt) {
        final int day = 30;
        long between = ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
        if (between <= day) {
            return NEW;
        }
        return DEFAULT;
    }
}
