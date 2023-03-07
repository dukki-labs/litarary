package com.litarary.book.controller;

import com.litarary.account.domain.InterestType;
import com.litarary.book.controller.dto.BookInfoDto;
import com.litarary.book.controller.dto.ConcernBookDto;
import com.litarary.book.controller.dto.ConcernBookTypeDto;
import com.litarary.book.controller.dto.RecentBookDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @GetMapping("/recent")
    public RecentBookDto.Response recentBookList(RecentBookDto.Request request) {
        List<BookInfoDto> bookInfoDtos = Arrays.asList(
                getBookInfo(12, 2, 3, InterestType.ART_LITERATURE),
                getBookInfo(3, 1, 2, InterestType.ART_LITERATURE),
                getBookInfo(2, 18, 5, InterestType.ART_LITERATURE)
        );
        return RecentBookDto.Response
                .builder()
                .bookInfoDtoList(bookInfoDtos)
                .build();
    }

    @GetMapping("/concern")
    public ConcernBookDto.Response concernBookList(ConcernBookDto request) {
        List<ConcernBookTypeDto> concernBookTypeDtos = Arrays.asList(
                ConcernBookTypeDto.builder()
                        .interestType(InterestType.HUMAN_RELATIONS)
                        .bookInfoDtoList(getBookList(InterestType.HUMAN_RELATIONS))
                        .build(),
                ConcernBookTypeDto.builder()
                        .interestType(InterestType.ART_LITERATURE)
                        .bookInfoDtoList(List.of(getBookInfo(2, 18, 6, InterestType.ART_LITERATURE)))
                        .build()
        );

        return ConcernBookDto.Response
                .builder()
                .concernBookTypeDto(concernBookTypeDtos)
                .build();
    }

    private List<BookInfoDto> getBookList(InterestType interestType) {
        return Arrays.asList(
                getBookInfo(12, 2, 3, interestType),
                getBookInfo(3, 1, 2, interestType),
                getBookInfo(2, 18, 5, interestType)
        );
    }

    private BookInfoDto getBookInfo(int likeCount, int viewCount, int minusNumber, InterestType interestType) {
        return BookInfoDto.builder()
                .imageUrl("https://www.taragrp.co.kr/wp-content/uploads/2022/07/%EB%8F%84%EC%84%9C-%EC%A0%9C%EB%B3%B8_01-2.png")
                .title("몸의 교감")
                .interestType(interestType)
                .content("테스형이 말씀하셨다. 우리가 먹는것이 곧 우리의 자신이 된다.")
                .likeCount(likeCount)
                .viewCount(viewCount)
                .regDt(LocalDateTime.now().minusHours(minusNumber))
                .build();
    }
}
