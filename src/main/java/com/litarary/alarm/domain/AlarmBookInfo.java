package com.litarary.alarm.domain;

import com.litarary.book.domain.RentalState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmBookInfo {
    private long bookRentalId;
    private RentalState rentalState;
    private long memberId;
    private String nickName;
    private long bookId;
    private String bookUrl;
    private String title;
}
