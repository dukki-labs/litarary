package com.litarary.book.service.dto;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public enum NewTag {
    NEW("신규도서"),
    DEFAULT("일반도서");

    private final String hint;

    public static NewTag isNewTag(LocalDateTime createdAt) {
        long days = Duration.between(createdAt, LocalDateTime.now()).toDays();
        if (days <= 30) {
            return NEW;
        }
        return DEFAULT;
    }
}
