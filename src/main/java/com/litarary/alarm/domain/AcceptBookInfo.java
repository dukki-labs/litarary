package com.litarary.alarm.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AcceptBookInfo {
    private long bookId;
    private AcceptState acceptState;
    private long memberId;
}

