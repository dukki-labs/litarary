package com.litarary.alarm.repository;

import com.litarary.alarm.domain.AlarmBookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmRepository {
    List<AlarmBookInfo> rentalBookHistoryList(@Param("memberId") Long memberId);
}
