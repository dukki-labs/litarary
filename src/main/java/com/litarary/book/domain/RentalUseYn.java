package com.litarary.book.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RentalUseYn {
    Y("대여가능"),
    N("대여불가능");

    private final String hint;
}
