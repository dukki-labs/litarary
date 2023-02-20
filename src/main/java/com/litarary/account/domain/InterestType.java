package com.litarary.account.domain;

import lombok.RequiredArgsConstructor;

/**
 *
 * IT/programs, Korean/foreign language, success/lifestyle, family life, stocks/finance, sales/sales, human relations, infants/children, adults, others
 */
@RequiredArgsConstructor
public enum InterestType {
    HISTORY_CULTURE("역사/문화"),
    POLITICAL_SOCIAL("정치/사회"),
    ECONOMY_OPERATE("경제/경영"),
    SELF_DEVELOPMENT("자기계발"),
    HEALTH_MEDICINE("건강/의학"),
    ART_LITERATURE("예술/문학"),
    SCIENCE_TECHNOLOGY("과학/기술"),
    HOBBIES_TRAVEL("취미/여행"),
    HUMANITIES_SOCIAL("인문/사회"),
    IT_PROGRAMS("IT/프로그램"),
    KOREAN_FOREIGN_LANGUAGE("국어/외국어"),
    SUCCESS_LIFESTYLE("성공/처세"),
    FAMILY_LIFE("가정생활"),
    STOCKS_FINANCE("주식/금융"),
    BUSINESS_SALES("영업/판매"),
    HUMAN_RELATIONS("인간관계"),
    INFANTS_CHILDREN("유아/아동"),
    ADULTS("성인"),
    OTHERS("기타");
    private final String message;
}
