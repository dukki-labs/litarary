package com.litarary.book.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.controller.dto.*;
import com.litarary.book.controller.mapper.BookMapper;
import com.litarary.book.service.BookService;
import com.litarary.book.service.dto.ContainerBookInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;


    @GetMapping("/recent")
    public RecentBookDto.Response recentBookList(RecentBookDto.Request request) {
        List<BookInfoDto> bookInfoDtos = Arrays.asList(
                getBookInfo(12, 2, 3, BookCategory.HISTORY),
                getBookInfo(3, 1, 2, BookCategory.HISTORY),
                getBookInfo(2, 18, 5, BookCategory.HISTORY)
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
                        .bookCategory(BookCategory.HISTORY)
                        .bookInfoDtoList(getBookList(BookCategory.COMIC_BOOK))
                        .build(),
                ConcernBookTypeDto.builder()
                        .bookCategory(BookCategory.Computer_Mobile)
                        .bookInfoDtoList(List.of(getBookInfo(2, 18, 6, BookCategory.Computer_Mobile)))
                        .build()
        );

        return ConcernBookDto.Response
                .builder()
                .concernBookTypeDto(concernBookTypeDtos)
                .build();
    }

    @GetMapping("/container/search")
    public ContainerBookInfoDto.Response searchContainerBookList(@RequestParam("searchKeyword") String searchKeyword,
                                                                 @PageableDefault Pageable pageable) {
        ContainerBookInfo containerBookInfo = bookService.searchBookListByContainer(searchKeyword, pageable);
        return bookMapper.toContainerBookInfoDto(containerBookInfo);
    }

    private List<BookInfoDto> getBookList(BookCategory bookCategory) {
        return Arrays.asList(
                getBookInfo(12, 2, 3, bookCategory),
                getBookInfo(3, 1, 2, bookCategory),
                getBookInfo(2, 18, 5, bookCategory)
        );
    }

    private BookInfoDto getBookInfo(int likeCount, int viewCount, int minusNumber, BookCategory bookCategory) {
        return BookInfoDto.builder()
                .imageUrl("https://www.taragrp.co.kr/wp-content/uploads/2022/07/%EB%8F%84%EC%84%9C-%EC%A0%9C%EB%B3%B8_01-2.png")
                .title("?????? ??????")
                .bookCategory(bookCategory)
                .content("???????????? ???????????????. ????????? ???????????? ??? ????????? ????????? ??????.")
                .likeCount(likeCount)
                .viewCount(viewCount)
                .regDt(LocalDateTime.now().minusHours(minusNumber))
                .build();
    }
}