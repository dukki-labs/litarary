package com.litarary.alarm.controller;

import com.litarary.alarm.domain.AlarmBookInfo;
import com.litarary.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
@RestController
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<AlarmBookInfo> alarmList(@RequestAttribute("memberId") Long memberId) {
        return alarmService.bookRentalTargetList(memberId);
    }
}
