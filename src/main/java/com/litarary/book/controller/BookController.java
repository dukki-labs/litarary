package com.litarary.book.controller;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.controller.dto.*;
import com.litarary.book.controller.mapper.BookMapper;
import com.litarary.book.service.BookService;
import com.litarary.book.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;


    @GetMapping("/recent/books")
    public RecentBookDto.Response recentBookList(@RequestParam("memberId") long memberId,
                                                 @RequestParam("size") int size) {

        List<BookInfoDto> bookInfoList = bookMapper.toBookInfoDto(bookService.recentBookList(memberId, size));

        return RecentBookDto.Response
                .builder()
                .bookInfoDtoList(bookInfoList)
                .build();
    }

    @PostMapping("/categories/{categoryId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void bookRegistration(@PathVariable Long categoryId, @RequestBody BookRegistrationDto.Request request) {
        bookService.registerBook(categoryId, bookMapper.toRegisterBook(request));
    }

    @PostMapping("/books/{bookId}/rental")
    @ResponseStatus(HttpStatus.OK)
    public void bookRental(@RequestAttribute("memberId") Long memberId,
                           @PathVariable Long bookId) {
        bookService.rentalBook(memberId, bookId);
    }

    @GetMapping("/books/{bookId}/detail")
    @ResponseStatus(HttpStatus.OK)
    public BookDetail bookDetail(@PathVariable Long bookId,
                                 @RequestAttribute("memberId") Long memberId) {
        return bookService.findBookDetail(bookId, memberId);
    }

    @GetMapping("/books/rentals")
    @ResponseStatus(HttpStatus.OK)
    public RentalBookDto.Response rentalBookList(@RequestAttribute("memberId") Long memberId,
                                                 @Valid @ModelAttribute("request") RentalBookDto.Request request) {
        RentalBook rentalBook = bookMapper.toRentalBook(request);
        PageBookContent rentalBookList = bookService.findRentalBookList(memberId, rentalBook);

        return RentalBookDto.Response
                .builder()
                .page(rentalBookList.getPage())
                .size(rentalBookList.getSize())
                .totalPage(rentalBookList.getTotalPage())
                .totalCount(rentalBookList.getTotalCount())
                .last(rentalBookList.isLast())
                .rentalBookResponseList(rentalBookList.getBookContentList())
                .build();
    }

    @GetMapping("/books/return")
    @ResponseStatus(HttpStatus.OK)
    public ReturnBook.Response findBookReturn(@RequestAttribute("memberId") Long memberId) {
        return bookService.findBookReturn(memberId);
    }

    @PostMapping("/books/return")
    @ResponseStatus(HttpStatus.OK)
    public void bookReturn(@RequestAttribute("memberId") Long memberId,
                           @RequestBody BookReturnDto.Request request) {
        ReturnBook.Request returnBook = bookMapper.toReturnBook(request);
        bookService.returnBook(memberId, returnBook);
    }

    @GetMapping("/books/most-borrowed")
    @ResponseStatus(HttpStatus.OK)
    public MostBorrowedBookDto.Response mostBorrowedBookList(@RequestAttribute("memberId") Long memberId,
                                                             @ModelAttribute MostBorrowedBookDto.Request request) {
        final int page = request.getPage() - 1;
        final int size = request.getSize();
        final PageRequest pageRequest = PageRequest.of(page, size);

        List<BookInfoDto> bookInfoDtos = bookMapper.toBookInfoDto(bookService.mostBorrowedBookList(memberId, pageRequest));

        return MostBorrowedBookDto.Response
                .builder()
                .bookInfoDtoList(bookInfoDtos)
                .build();
    }

    @GetMapping("/books/concern")
    public ConcernBookDto.Response concernBookList(ConcernBookDto request) {
        List<ConcernBookTypeDto> concernBookTypeDtos = Arrays.asList(
                ConcernBookTypeDto.builder()
                        .bookCategory(BookCategory.SCIENCE_TECHNOLOGY)
                        .bookInfoDtoList(getBookList(BookCategory.EDUCATION))
                        .build(),
                ConcernBookTypeDto.builder()
                        .bookCategory(BookCategory.COMPUTER_MOBILE)
                        .bookInfoDtoList(List.of(getBookInfo(2, 18, 6, BookCategory.COMPUTER_MOBILE)))
                        .build()
        );

        return ConcernBookDto.Response
                .builder()
                .concernBookTypeDto(concernBookTypeDtos)
                .build();
    }

    @GetMapping("/books/container/search")
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
                .title("몸의 교감")
                .category(bookCategory)
                .content("테스형이 말씀하셨다. 우리가 먹는것이 곧 우리의 자신이 된다.")
                .recommendCount(likeCount)
                .regDt(LocalDateTime.now().minusHours(minusNumber))
                .build();
    }
}