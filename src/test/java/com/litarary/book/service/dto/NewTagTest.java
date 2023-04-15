package com.litarary.book.service.dto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NewTagTest {

    @ParameterizedTest(name = "[{index}] => {0}는 현재 날짜기준으로 30일이 지났으면 DEFAULT, 30일 이내이면 NEW를 반환한다.")
    @MethodSource("createTimeSource")
    void newTagTest2(LocalDateTime day, NewTag expected) {
        NewTag result = NewTag.isNewTag(day);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> createTimeSource() {
        return Stream.of(
                Arguments.of(LocalDateTime.now().minusDays(29), NewTag.NEW),
                Arguments.of(LocalDateTime.now().minusDays(30), NewTag.NEW),
                Arguments.of(LocalDateTime.now().minusDays(31), NewTag.DEFAULT),
                Arguments.of(LocalDateTime.now().minusDays(32), NewTag.DEFAULT)
        );
    }
}