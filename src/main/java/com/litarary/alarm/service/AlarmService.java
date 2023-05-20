package com.litarary.alarm.service;

import com.litarary.alarm.domain.AlarmBookInfo;
import com.litarary.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public List<AlarmBookInfo> bookRentalTargetList(Long memberId) {
        return alarmRepository.rentalBookHistoryList(memberId);
    }

}
