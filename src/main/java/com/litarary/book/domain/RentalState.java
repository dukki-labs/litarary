package com.litarary.book.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RentalState {
    RENTAL("대여"),
    RETURN("반납"),
    REQUEST("요청");

    private final String hint;

    public boolean isRental() {
        return this == RENTAL;
    }
}
