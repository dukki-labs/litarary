package com.litarary.book.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchType {
    NEW("신규순"),
    RECOMMEND("추천순")
    ;
    private final String hint;
}
