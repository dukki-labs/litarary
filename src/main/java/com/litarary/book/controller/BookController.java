package com.litarary.book.controller;

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

    @GetMapping("/books/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchBookDto.Response searchBook(@RequestAttribute("memberId") Long memberId,
                                             @ModelAttribute("request") SearchBookDto.Request request) {
        SearchBookInfo.Request requestDto = bookMapper.toSearchBookRequest(request, memberId);
        SearchBookInfo.Response response = bookService.searchBook(requestDto);

        return SearchBookDto.Response
                .builder()
                .page(response.getPage())
                .size(response.getSize())
                .totalPage(response.getTotalPage())
                .totalCount(response.getTotalCount())
                .last(response.isLast())
                .bookInfoList(response.getBookContentList())
                .build();
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

    @GetMapping("/books/container/search")
    public ContainerBookInfoDto.Response searchContainerBookList(@RequestParam("searchKeyword") String searchKeyword,
                                                                 @PageableDefault Pageable pageable) {
        ContainerBookInfo containerBookInfo = bookService.searchBookListByContainer(searchKeyword, pageable);
        return bookMapper.toContainerBookInfoDto(containerBookInfo);
    }
}