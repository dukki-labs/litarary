package com.litarary.book.controller.dto;

import com.litarary.account.domain.InterestType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class ConcernBookTypeDto {
    @NotNull
    private InterestType interestType;

    private List<BookInfoDto> bookInfoDtoList;
}
