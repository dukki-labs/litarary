package com.litarary.account.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookCategory {
    HISTORY_CULTURE("역사/예술/문화"),
    EDUCATION("교육"),
    FAMILY_LIFE("가정/요리/뷰티"),
    HOBBY("건강/취미/레저/여행"),
    ECONOMIC_MANAGEMENT("경제경영"),
    SCIENCE_TECHNOLOGY("사회과학/과학"),
    COMPUTER_MOBILE("컴퓨터/모바일"),
    LITERATURE("문학"),
    ENTERTAINMENT("엔터테인먼트"),
    SELF_DEVELOPMENT("자기계발"),
    LANGUAGE("언어"),
    OTHER("기타");

    private final String message;
}
