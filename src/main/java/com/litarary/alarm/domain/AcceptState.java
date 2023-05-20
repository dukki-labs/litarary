package com.litarary.alarm.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public
enum AcceptState {
    ACCEPT("수락"),
    REFUSE("거절");

    private final String hint;
}
