package com.litarary.account.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookCategory {
    HISTORY_CULTURE("가정/요리/뷰티"),
    HOBBY("취미"),
    ECONOMY_OPERATE("경제경영"),
    HIGH_SCHOOL("고등학교참고서"),
    CLASSIC("고전"),
    SCIENCE_TECHNOLOGY("과학"),
    KUMGANG_TRAVEL("금강산여행"),
    CALENDAR("달력"),
    UNIVERSITY_BOOK("대학교제"),
    COMIC_BOOK("만화"),
    SOCIAL_SCIENCE("사회과학"),
    NOVEL_POETRY_DRAMA("소설/시/희곡"),
    CALIFIER_CERTIFICATE("수험서/자격증"),
    CHILDREN("어린이"),
    ESSAY("에세이"),
    TRAVEL("여행"),
    HISTORY("역사"),
    ART_POPULAR_CULTURE("예술/대중문화"),
    FOREIGN_LANGUAGE("외국어"),
    CHILD("유아"),
    HUMANITIES("인문학"),
    JAPANESE_BOOK("일본 도서"),
    SELF_DEVELOPMENT("자기계발"),
    MAGAZINE("잡지"),
    GENRE("장르소설"),
    COMPLETE_USED_COLLECTION("전집/중고전집"),
    RELIGION_MECHANICS("종교/역학"),
    GOOD_PARENT("좋은부모"),
    MIDDLE_SCHOOL("중학교참고서"),
    TEENAGER("청소년"),
    YOUTH_RECOMMENDATION("청소년_추천도서"),
    Elementary_School("초등학교참고서"),
    Computer_Mobile("컴퓨터/모바일"),
    Gift("Gift"),
    OTHER("기타");

    private final String message;
}
