package com.litarary.alarm.controller.dto;

import com.litarary.alarm.domain.AcceptState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptBookDto {
    private Long bookId;
    private AcceptState acceptState;
}
